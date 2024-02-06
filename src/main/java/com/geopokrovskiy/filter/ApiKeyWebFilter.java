package com.geopokrovskiy.filter;

import com.geopokrovskiy.entity.Status;
import com.geopokrovskiy.entity.UserEntity;
import com.geopokrovskiy.repository.UserRedisRepository;
import com.geopokrovskiy.repository.UserRepository;
import com.geopokrovskiy.service.ApiService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

@Component
@Data
@Slf4j
@AllArgsConstructor
public class ApiKeyWebFilter implements WebFilter {
    private final String apiKeyHeader = "X-API-Key";
    private final ApiRateLimiter rateLimiter;
    private final ApiService apiService;
    private final PathPatternParser pathPatternParser = new PathPatternParser();
    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;

    // TODO add an opportunity to get forecasts from database (via redis)
    // TODO Tests

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (shouldApplyFilter(exchange)) {
            String apiKey = exchange.getRequest().getHeaders().getFirst(apiKeyHeader);
            String userName = apiService.restoreUsername(apiKey);
            Mono<UserEntity> userEntityMono = this.userRedisRepository.getUserFromRedis(userName)
                    .switchIfEmpty(Mono.defer(() -> this.userRepository.findByUsername(userName)));
            return userEntityMono.flatMap(u -> {
                        if (u.isBlocked()) {
                            return Mono.empty().then(Mono.defer(() -> {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                log.error("The user has been blocked!");
                                return Mono.empty();
                            }));
                        }
                        Status status = u.getStatus();
                        if (apiKey == null || !rateLimiter.tryConsume(apiKey, status)) {
                            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                            return Mono.empty();
                        }
                        return chain.filter(exchange);
                    }).switchIfEmpty(Mono.empty())
                    .then(Mono.defer(() -> {
                        exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                        log.error("The user has not been found!");
                        return Mono.empty();
                    }));
        }

        return chain.filter(exchange);
    }

    private boolean shouldApplyFilter(ServerWebExchange exchange) {
        PathPattern pathPattern = this.pathPatternParser.parse("/api/v1/stations/**");
        return pathPattern.matches(exchange.getRequest().getPath());
    }


}

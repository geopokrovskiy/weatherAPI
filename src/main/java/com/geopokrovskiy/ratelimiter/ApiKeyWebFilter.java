package com.geopokrovskiy.ratelimiter;

import com.geopokrovskiy.entity.Status;
import com.geopokrovskiy.service.ApiService;
import com.geopokrovskiy.service.IApiService;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@AllArgsConstructor
public class ApiKeyWebFilter implements WebFilter {
    private final String apiKeyHeader = "X-API-Key";
    private final ApiRateLimiter rateLimiter = new ApiRateLimiter();
    private final IApiService apiService = new ApiService();
    private final PathPatternParser pathPatternParser = new PathPatternParser();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (shouldApplyFilter(exchange)) {
            String apiKey = exchange.getRequest().getHeaders().getFirst(apiKeyHeader);
            Object[] usernameAndSubscription = apiService.restoreUsernameAndSubscription(apiKey);
            String username = (String) usernameAndSubscription[0];
            Status status = (Status) usernameAndSubscription[1];
            if (apiKey == null || !rateLimiter.tryConsume(apiKey, status)) {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return Mono.empty();
            }
        }

        return chain.filter(exchange);
    }

    private boolean shouldApplyFilter(ServerWebExchange exchange) {
        PathPattern pathPattern = this.pathPatternParser.parse("/api/v1/stations/**");
        return pathPattern.matches(exchange.getRequest().getPath());
    }


}

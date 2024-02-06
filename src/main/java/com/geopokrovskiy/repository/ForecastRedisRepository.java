package com.geopokrovskiy.repository;

import com.geopokrovskiy.entity.ForecastEntity;
import com.geopokrovskiy.entity.StationEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Data
@Slf4j
public class ForecastRedisRepository {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, ForecastEntity> forecastOps;
    private final String forecast = "_FORECAST";

    public Mono<ForecastEntity> addForecastToRedis(ForecastEntity forecastEntity) {
        return forecastOps.opsForValue()
                .set(forecastEntity.getStationCode() + forecast, forecastEntity)
                .mapNotNull(success -> success ? forecastEntity : null);
    }

    public Mono<ForecastEntity> getForecastFromRedis(StationEntity stationEntity) {
        return forecastOps.opsForValue().get(stationEntity.getCode() + forecast);
    }

    public Mono<Boolean> deleteForecastFromRedis(String stationCode) {
        return forecastOps.opsForValue().delete(stationCode + forecast);
    }
}

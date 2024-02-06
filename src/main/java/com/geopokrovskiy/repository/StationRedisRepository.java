package com.geopokrovskiy.repository;

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
public class StationRedisRepository {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, StationEntity> stationOps;

    public Mono<StationEntity> addStationToRedis(StationEntity stationEntity) {
        return stationOps.opsForValue()
                .set(stationEntity.getCode(), stationEntity)
                .mapNotNull(success -> success ? stationEntity : null);
    }

    public Mono<StationEntity> getStationByStationCode(String stationCode){
        return stationOps.opsForValue().get(stationCode);
    }
}

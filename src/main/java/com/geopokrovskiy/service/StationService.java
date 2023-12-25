package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.StationEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
@Slf4j
public class StationService {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, StationEntity> stationOps;

}

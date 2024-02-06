package com.geopokrovskiy.repository;


import com.geopokrovskiy.entity.StationEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface StationRepository extends R2dbcRepository<StationEntity, String> {
    Mono<StationEntity> findByCode(String code);
}

package com.geopokrovskiy.repository;

import com.geopokrovskiy.entity.ForecastEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ForecastRepository extends R2dbcRepository<ForecastEntity, Integer> {
}

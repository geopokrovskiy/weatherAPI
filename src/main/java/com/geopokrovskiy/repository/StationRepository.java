package com.geopokrovskiy.repository;

import com.geopokrovskiy.entity.StationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StationRepository extends ReactiveCrudRepository<StationEntity, String> {
}

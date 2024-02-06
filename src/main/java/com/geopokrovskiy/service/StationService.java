package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.ForecastEntity;
import com.geopokrovskiy.entity.StationEntity;
import com.geopokrovskiy.repository.StationRedisRepository;
import com.geopokrovskiy.repository.StationRepository;
import com.geopokrovskiy.utils.RandomForecastGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
@Slf4j
public class StationService {
    private final StationRepository stationRepository;
    private final StationRedisRepository stationRedisRepository;
    private final ForecastService forecastService;

    public Mono<StationEntity> addStation(StationEntity stationEntity) {
        return stationRepository.save(stationEntity).flatMap(savedStation ->
                forecastService.addForecastToNewStation(savedStation)
                        .then(stationRedisRepository.addStationToRedis(savedStation))
                        .thenReturn(savedStation).doOnSuccess(s ->
                                log.info("The station {} has been added!", s)
                        ));
    }
}

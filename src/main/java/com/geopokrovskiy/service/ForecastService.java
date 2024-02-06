package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.ForecastEntity;
import com.geopokrovskiy.entity.StationEntity;
import com.geopokrovskiy.repository.ForecastRedisRepository;
import com.geopokrovskiy.repository.ForecastRepository;
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
public class ForecastService {
    private final ForecastRedisRepository forecastRedisRepository;
    private final ForecastRepository forecastRepository;
    private final RandomForecastGenerator randomForecastGenerator;
    private final StationRedisRepository stationRedisRepository;
    private final StationRepository stationRepository;

    public Mono<ForecastEntity> addForecastToNewStation(StationEntity stationEntity) {
        ForecastEntity forecastEntity = randomForecastGenerator.generateRandomForecast();
        forecastEntity.setStationCode(stationEntity.getCode());
        return forecastRepository.save(forecastEntity).flatMap(savedForecast ->
                forecastRedisRepository.addForecastToRedis(savedForecast).thenReturn(savedForecast).doOnSuccess(f ->
                        log.info("A new forecast for the station {} has been generated!", forecastEntity.getStationCode()))
        );
    }

    public Mono<ForecastEntity> getForecastByStationCode(String stationCode) {
        return this.stationRedisRepository.getStationByStationCode(stationCode)
                .switchIfEmpty(this.stationRepository
                        .findByCode(stationCode)
                        .flatMap(this::writeStationToRedis))
                .flatMap(this.forecastRedisRepository::getForecastFromRedis);
    }

    private Mono<StationEntity> writeStationToRedis(StationEntity stationEntity) {
        return this.stationRedisRepository.addStationToRedis(stationEntity);
    }
}

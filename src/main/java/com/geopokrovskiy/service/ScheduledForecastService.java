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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Data
@Slf4j
@EnableScheduling
public class ScheduledForecastService {
    private final ForecastRedisRepository forecastRedisRepository;
    private final ForecastRepository forecastRepository;
    private final StationRedisRepository stationRedisRepository;
    private final StationRepository stationRepository;
    private final RandomForecastGenerator randomForecastGenerator;

    @Scheduled(fixedRate = 300 * 1000) // generates random forecasts every 5 minutes
    public void generateAndSaveRandomForecasts() {
        Flux<StationEntity> stationEntities = stationRepository.findAll();

        stationEntities.flatMap(stationEntity -> {
            ForecastEntity newForecast = this.getRandomForecastGenerator().generateRandomForecast();
            String stationCode = stationEntity.getCode();
            newForecast.setStationCode(stationCode);

            Mono<ForecastEntity> saveNewForecastToPostgresMono = this.forecastRepository.save(newForecast);
            Mono<Boolean> deleteOldForecastFromRedisMono = this.forecastRedisRepository.deleteForecastFromRedis(stationCode);
            Mono<ForecastEntity> saveNewForecastToRedisMono = this.forecastRedisRepository.addForecastToRedis(newForecast);
            return saveNewForecastToPostgresMono.flatMap(postgresResult ->
                    deleteOldForecastFromRedisMono.flatMap(deletedRedisResult ->
                            saveNewForecastToRedisMono.map(redisResult -> {
                                log.info("The forecast on the station {} has been successfully updated!", redisResult.getStationCode());
                                return redisResult;
                            })
                    )
            );
        }).subscribe();
    }
}

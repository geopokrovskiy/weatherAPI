package com.geopokrovskiy.controller;

import com.geopokrovskiy.dto.StationDto;
import com.geopokrovskiy.entity.StationEntity;
import com.geopokrovskiy.mapper.StationMapper;
import com.geopokrovskiy.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stations")
public class StationRestController {
    @Autowired
    private final ReactiveRedisOperations<String, StationEntity> stationOps;
    @Autowired
    private final StationMapper stationMapper;

/*    public Mono<StationDto> addNewStation(@RequestBody StationDto stationDto) {
        StationEntity stationEntity = stationMapper.map(stationDto);
        return stationService.addStation(stationEntity).map(stationMapper::map);
    }*/

}

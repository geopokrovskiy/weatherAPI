package com.geopokrovskiy.controller;

import com.geopokrovskiy.dto.StationDto;
import com.geopokrovskiy.entity.StationEntity;
import com.geopokrovskiy.mapper.StationMapper;
import com.geopokrovskiy.service.ApiService;
import com.geopokrovskiy.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stations")
public class StationRestController {
    @Autowired
    private final StationService stationService;
    @Autowired
    private final StationMapper stationMapper;
    @Autowired
    private final ApiService apiService;

    @PostMapping("/addStation")
    public Mono<StationDto> addStation(@RequestBody StationDto stationDto) {
        StationEntity stationEntity = stationMapper.map(stationDto);
        return stationService.addStation(stationEntity).map(stationMapper::map);
    }

}

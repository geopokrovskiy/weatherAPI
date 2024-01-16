package com.geopokrovskiy.mapper;

import com.geopokrovskiy.dto.StationDto;
import com.geopokrovskiy.entity.StationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StationMapper {
    StationDto map(StationEntity stationEntity);

    @InheritInverseConfiguration
    StationEntity map(StationDto stationDto);

}

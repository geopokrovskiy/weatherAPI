package com.geopokrovskiy.mapper;

import com.geopokrovskiy.dto.StationDto;
import com.geopokrovskiy.entity.StationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StationMapper {
    @InheritInverseConfiguration
    StationDto map(StationEntity stationEntity);
    StationEntity map(StationDto stationDto);

}

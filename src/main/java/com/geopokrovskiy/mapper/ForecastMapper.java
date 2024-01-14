package com.geopokrovskiy.mapper;

import com.geopokrovskiy.dto.ForecastDto;
import com.geopokrovskiy.entity.ForecastEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForecastMapper {
    @InheritInverseConfiguration
    ForecastDto map(ForecastEntity forecastEntity);
    ForecastEntity map(ForecastDto forecastDto);

}

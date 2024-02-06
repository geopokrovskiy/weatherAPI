package com.geopokrovskiy.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.geopokrovskiy.entity.CloudOctant;
import com.geopokrovskiy.entity.CloudType;
import com.geopokrovskiy.entity.Precipitations;
import com.geopokrovskiy.entity.StationEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ForecastDto {
    private Integer id;
    private StationEntity station;
    private LocalDateTime time;
    private Double temperature;
    private Precipitations precipitations;
    private Integer windSpeed;
    private Double windAngle;
    private CloudType cloudType;
    private CloudOctant cloudOctant;
}

package com.geopokrovskiy.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.geopokrovskiy.entity.CloudOctant;
import com.geopokrovskiy.entity.CloudType;
import com.geopokrovskiy.entity.Precipitations;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class StationDto {
    private String code;
    private Double temperature;
    private Precipitations precipitations;
    private Integer windSpeed;
    private Double windAngle;
    private CloudType cloudType;
    private CloudOctant cloudOctant;
}

package com.geopokrovskiy.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.geopokrovskiy.entity.CloudOctant;
import com.geopokrovskiy.entity.CloudType;
import com.geopokrovskiy.entity.ForecastEntity;
import com.geopokrovskiy.entity.Precipitations;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class StationDto {
    @Id
    private Integer id;
    private String code;
    private String city;
}

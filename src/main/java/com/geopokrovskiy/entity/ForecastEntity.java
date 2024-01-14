package com.geopokrovskiy.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("forecasts")
public class ForecastEntity {
    @Id
    private Integer id;
    @Transient
    private StationEntity station;
    private Double temperature;
    private Precipitations precipitations;
    private Integer windSpeed;
    private Double windAngle;
    private CloudType cloudType;
    private CloudOctant cloudOctant;
}

package com.geopokrovskiy.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("forecasts")
public class ForecastEntity {
    @Id
    private Integer id;
    @Column("station")
    private String stationCode;
    @Column("time")
    private LocalDateTime time;
    @Column("temperature")
    private Double temperature;
    @Column("precipitations")
    private Precipitations precipitations;
    @Column("wind_speed")
    private Integer windSpeed;
    @Column("wind_angle")
    private Double windAngle;
    @Column("cloud_type")
    private CloudType cloudType;
    @Column("cloud_octant")
    private CloudOctant cloudOctant;
}

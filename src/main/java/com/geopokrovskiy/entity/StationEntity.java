package com.geopokrovskiy.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("stations")
public class StationEntity {
    @Id
    private String code;
    private String city;
    @Transient
    @ToString.Exclude
    private List<ForecastEntity> forecasts;

}

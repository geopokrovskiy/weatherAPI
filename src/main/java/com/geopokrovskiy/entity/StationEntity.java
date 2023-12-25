package com.geopokrovskiy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@RedisHash("StationEntity")
public class StationEntity {
    @Id
    private String code;
    private Double temperature;
    private Precipitations precipitations;
    private Integer windSpeed;
    private Double windAngle;
    private CloudType cloudType;
    private CloudOctant cloudOctant;

}

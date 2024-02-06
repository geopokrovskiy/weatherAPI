package com.geopokrovskiy.repository;

import com.geopokrovskiy.entity.StationEntity;
import com.geopokrovskiy.entity.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Data
@Slf4j
public class UserRedisRepository {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, UserEntity> userOps;

    public Mono<UserEntity> addUserToRedis(UserEntity userEntity) {
        return userOps.opsForValue()
                .set(userEntity.getUsername(), userEntity)
                .mapNotNull(success -> success ? userEntity : null);
    }

    public Mono<UserEntity> getUserFromRedis(String username) {
        return userOps.opsForValue().get(username);
    }
}

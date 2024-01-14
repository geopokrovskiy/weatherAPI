package com.geopokrovskiy.config;

import com.geopokrovskiy.entity.StationEntity;
import com.geopokrovskiy.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.userdetails.User;

@Configuration
public class UserEntityRedisConfiguration {
    @Bean
    ReactiveRedisOperations<String, UserEntity> redisUserOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<UserEntity> serializer = new Jackson2JsonRedisSerializer<>(UserEntity.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, UserEntity> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, UserEntity> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}

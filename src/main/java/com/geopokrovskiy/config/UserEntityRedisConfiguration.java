package com.geopokrovskiy.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.geopokrovskiy.entity.UserEntity;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Data
public class UserEntityRedisConfiguration {
    private final RedisObjectMapper redisObjectMapper;
    @Bean
    ReactiveRedisOperations<String, UserEntity> redisUserOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<UserEntity> serializer = new Jackson2JsonRedisSerializer<>(redisObjectMapper.objectMapper(), UserEntity.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, UserEntity> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, UserEntity> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}

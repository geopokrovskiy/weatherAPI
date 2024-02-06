package com.geopokrovskiy.config;

import com.geopokrovskiy.entity.ForecastEntity;
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
public class ForecastEntityRedisConfiguration {
    private final RedisObjectMapper redisObjectMapper;
    @Bean
    ReactiveRedisOperations<String, ForecastEntity> redisForecastOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<ForecastEntity> serializer = new Jackson2JsonRedisSerializer<>(redisObjectMapper.objectMapper(), ForecastEntity.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, ForecastEntity> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, ForecastEntity> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }


}

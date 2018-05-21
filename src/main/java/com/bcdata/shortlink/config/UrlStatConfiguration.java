package com.bcdata.shortlink.config;

import com.bcdata.shortlink.entity.UrlStat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * The configuration of the url stat about redis
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 20:15
 */
@Configuration
public class UrlStatConfiguration {

    @Bean
    ReactiveRedisOperations<String, UrlStat> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<UrlStat> serializer = new Jackson2JsonRedisSerializer<UrlStat> (UrlStat.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, UrlStat> builder =
                RedisSerializationContext.newSerializationContext (new StringRedisSerializer ());

        RedisSerializationContext<String, UrlStat> context = builder.value (serializer).build ();

        return new ReactiveRedisTemplate<> (factory, context);
    }
}

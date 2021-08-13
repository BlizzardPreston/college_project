package com.association.common.cache;

import com.association.common.cache.api.CacheManager;
import com.association.config.redis.RedisManagerFactory;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class CacheManagerConfiguration {

    @Bean("stringRedisTemplate")
    @ConditionalOnBean(PrimaryRedisProperties.class)
    public StringRedisTemplate stringRedisTemplate(@Autowired @Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean(name = "longRedisTemplate")
    @ConditionalOnBean(PrimaryRedisProperties.class)
    public RedisTemplate<String, Long> counterRedisTemplate(@Autowired @Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        template.setHashValueSerializer(new GenericToStringSerializer<>(Long.class));
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean("lettuceConnectionFactory")
    @ConditionalOnBean(PrimaryRedisProperties.class)
    public LettuceConnectionFactory lettuceConnectionFactory(@Autowired @Qualifier("primaryRedisProperties") PrimaryRedisProperties primaryRedisProperties) {

        if (log.isDebugEnabled()) {
            log.debug("redis properties: {}", primaryRedisProperties);
        }
        return RedisManagerFactory.initFactory(primaryRedisProperties);
    }

    @Bean(name = "objectRedisTemplate")
    @ConditionalOnBean(PrimaryRedisProperties.class)
    public RedisTemplate<String, Object> objectRedisTemplate(@Autowired @Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean(name = "cacheManager")
    @ConditionalOnBean(PrimaryRedisProperties.class)
    public CacheManager cacheManager(@Autowired @Qualifier("stringRedisTemplate") StringRedisTemplate stringRedisTemplate, @Autowired @Qualifier("primaryRedisProperties") PrimaryRedisProperties primaryRedisProperties) {

        CacheManager cacheManager = new RedisManager();
        cacheManager.init(stringRedisTemplate, primaryRedisProperties);
        return cacheManager;
    }

    @Bean
    @ConditionalOnBean(PrimaryRedisProperties.class)
    public RedisTemplate<String, Object> redisTemplate(@Autowired @Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        template.setConnectionFactory(lettuceConnectionFactory);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 无效的映射字段不会抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);// 无效的映射字段不会抛出异常
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer); // value的序列化类型
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }
}

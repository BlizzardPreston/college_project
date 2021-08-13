package com.association.config.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

public class RedisManagerFactory {
    public static LettuceConnectionFactory initFactory(RedisProperties redisProperties){
        GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
        RedisProperties.Lettuce lettuce=redisProperties.getLettuce();
        if (lettuce != null) {
            RedisProperties.Pool pool = lettuce.getPool();
            if (pool != null) {
                poolConfig.setMaxIdle(pool.getMaxIdle());
                poolConfig.setMinIdle(pool.getMinIdle());
                poolConfig.setMaxTotal(pool.getMaxActive());
                poolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
                if (pool.getTimeBetweenEvictionRuns() != null) {
                    poolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRuns().toMillis());
                }
                if (pool.getMaxWait() != null) {
                    poolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
                }
            }
        }

        LettucePoolingClientConfiguration lettucePoolingClientConfiguration= LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig).build();
        RedisConfiguration redisConfig=null;

        //单机
        redisConfig = new RedisStandaloneConfiguration();
        ((RedisStandaloneConfiguration) redisConfig).setHostName(redisProperties.getHost());
        ((RedisStandaloneConfiguration) redisConfig).setPort(redisProperties.getPort());
        ((RedisStandaloneConfiguration) redisConfig).setPassword(RedisPassword.of(redisProperties.getPassword()));
        ((RedisStandaloneConfiguration) redisConfig).setDatabase(redisProperties.getDatabase());


        //返回
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
        return factory;
    }
}

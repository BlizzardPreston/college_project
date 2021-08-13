package com.association.common.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redis")
@ConditionalOnProperty(prefix = "redis",name = "database")
//获取配置文件信息到类封装以便配置工厂
public class PrimaryRedisProperties extends RedisProperties {
}

package com.association.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: SK•YOUNG
 * @Date: 2020-04-06-0006 2:19
 * @Description:
 **/
@Configuration
public class ShiroLifecycleBeanPostProcessorConfig {
    /**
     * Shiro生命周期处理器
     *
     * @return LifecycleBeanPostProcessor
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}

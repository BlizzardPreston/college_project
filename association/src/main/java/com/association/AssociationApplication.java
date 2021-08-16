package com.association;

import com.association.common.cache.PrimaryRedisProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(scanBasePackages = "com.association")
@MapperScan("com.association.Dao")
@EnableCaching

public class AssociationApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AssociationApplication.class, args);
        SpringApplication.run(AssociationApplication.class, args);
    }

//    public void first() {
//        PrimaryRedisProperties primaryRedisProperties=new PrimaryRedisProperties();
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

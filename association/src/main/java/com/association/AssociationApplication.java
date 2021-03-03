package com.association;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.association.Dao")
public class AssociationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssociationApplication.class, args);
    }

}

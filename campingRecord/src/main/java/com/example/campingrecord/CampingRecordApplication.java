package com.example.campingrecord;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.campingrecord.mapper")
public class CampingRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampingRecordApplication.class, args);
    }

}

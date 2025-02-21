package com;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@Slf4j
@EnableCaching
@MapperScan("com.admissionstudent_server.mapper")
public class AdmissionStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdmissionStudentApplication.class, args);
    }

}

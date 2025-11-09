package com.mayanshe.scrmstd.saasapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.mayanshe.scrmstd.saasapi",
                "com.mayanshe.scrmstd.shared",
                "com.mayanshe.scrmstd.infrastructure",
                "com.mayanshe.scrmstd.application"
        }
)
@MapperScan("com.mayanshe.scrmstd.infrastructure.persistence.mapper")
public class SaasApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaasApiApplication.class, args);
    }
}

package com.mayanshe.scrmstd.bossapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.mayanshe.scrmstd.bossapi",
                "com.mayanshe.scrmstd.shared",
                "com.mayanshe.scrmstd.infrastructure",
                "com.mayanshe.scrmstd.application"
        }
)
//@MapperScan("com.mayanshe.scrmstd.infrastructure.persistence.mapper")
public class BossApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BossApiApplication.class, args);
    }
}

package com.mayanshe.scrmstd.scheduletask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.mayanshe.scrmstd.scheduletask",
                "com.mayanshe.scrmstd.shared",
                "com.mayanshe.scrmstd.infrastructure",
                "com.mayanshe.scrmstd.application"
        }
)
public class ScheduleTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleTaskApplication.class, args);
    }
}

package com.printmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrintManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrintManagementApplication.class, args);
    }
}
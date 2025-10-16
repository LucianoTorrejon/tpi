package com.tpi.ratescosts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RatesCostsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatesCostsServiceApplication.class, args);
    }
}

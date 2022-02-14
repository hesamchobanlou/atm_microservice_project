package com.example.atm_discovery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AtmDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmDiscoveryServiceApplication.class, args);
    }

}

package com.mjubus.busserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusServerApplication.class, args);
    }

}

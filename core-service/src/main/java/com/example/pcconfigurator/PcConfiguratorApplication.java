package com.example.pcconfigurator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class PcConfiguratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcConfiguratorApplication.class, args);
    }
}


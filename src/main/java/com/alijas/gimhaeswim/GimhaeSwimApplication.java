package com.alijas.gimhaeswim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GimhaeSwimApplication {

    public static void main(String[] args) {
        SpringApplication.run(GimhaeSwimApplication.class, args);
    }

}

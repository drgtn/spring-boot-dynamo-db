package com.drgtn;

import com.drgtn.service.impl.DataDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SymptomCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SymptomCheckerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(DataDefinitionService dataDefinitionService) {

        return args -> {
            log.info("Starting data seeding...");
            dataDefinitionService.createUserTable();
            log.info("Data seeding completed!");

        };
    }

}

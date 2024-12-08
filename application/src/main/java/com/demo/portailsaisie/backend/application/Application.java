package com.demo.portailsaisie.backend.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.demo.portailsaisie.backend"})
@EnableJpaRepositories("com.demo.portailsaisie.backend.core.repository")
@EntityScan("com.demo.portailsaisie.backend.core.domain")
public class Application {

    private static final String DEFAULT_PROFILE = "dev";
    public static void main(String[] args) {
         SpringApplication application = new SpringApplication(Application.class);
         SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
         addDefaultProfile(source, application);
        application.run(args);
    }

    private static void addDefaultProfile(SimpleCommandLinePropertySource source, SpringApplication application) {
        if (!source.containsProperty("spring.profiles.active") && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
            application.setAdditionalProfiles(DEFAULT_PROFILE);
        }
    }
}

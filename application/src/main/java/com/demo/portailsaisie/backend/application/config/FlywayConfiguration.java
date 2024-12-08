package com.demo.portailsaisie.backend.application.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfiguration {

  @Bean
  public FlywayMigrationStrategy cleanMigrateStrategy() {
    return flyway -> {
      flyway.repair();
      flyway.migrate();
    };
  }
  @Bean
  public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
    return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
  }
}

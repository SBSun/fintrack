package com.sun.fintrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class FintrackApplication {

  public static void main(String[] args) {

    SpringApplication.run(FintrackApplication.class, args);
  }

  @Bean
  public InMemoryHttpExchangeRepository httpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }
}

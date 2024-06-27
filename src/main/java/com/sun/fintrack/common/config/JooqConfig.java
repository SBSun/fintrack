package com.sun.fintrack.common.config;

import com.sun.fintrack.common.listener.LoggingExecuteListener;

import org.jooq.ExecuteListenerProvider;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {
  @Bean
  public ExecuteListenerProvider executeListenerProvider() {
    return new DefaultExecuteListenerProvider(new LoggingExecuteListener());
  }
}

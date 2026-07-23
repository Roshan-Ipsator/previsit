package com.previsit.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GroqWebClientConfig {
  @Bean
  public WebClient groqWebClient() {
    return WebClient.builder().baseUrl("https://api.groq.com/openai").build();
  }
}
package com.previsit.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ApplicationConfig {

  @Value("${spring.mail.host}")
  private String emailHost;

  @Value("${spring.mail.port}")
  private int emailPort;

  @Value("${spring.mail.username}")
  private String emailUser;

  @Value("${spring.mail.password}")
  private String emailPass;

//  @Bean
//  public JavaMailSender javaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    mailSender.setHost(emailHost);
//    mailSender.setPort(emailPort);
//    mailSender.setUsername(emailUser);
//    mailSender.setPassword(emailPass);
//    return mailSender;
//  }

}

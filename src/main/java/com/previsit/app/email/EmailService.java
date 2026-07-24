package com.previsit.app.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

  final JavaMailSender mailSender;

  @Value("${email.sender}")
  private String senderEmail;
  @Value("${sender.name}")
  private String senderName;

  @Autowired
  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Async
  public void sendEmail(String emailId, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(senderName + " <" + senderEmail + ">");
    message.setTo(emailId);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
  }

}

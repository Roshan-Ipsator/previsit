package com.previsit.app.email;

import com.previsit.app.email.request.EmailRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

  @Autowired
  final EmailService emailService;

  @PostMapping
  public ResponseEntity<?> sendEmail(@Valid @RequestBody EmailRequest emailRequest){
    emailService.sendEmail(emailRequest.emailId(), emailRequest.subject(), emailRequest.body());
    return ResponseEntity.ok("Email sent.");
  }
}

package com.previsit.app.user;

import com.previsit.app.api.response.ApiResponse;
import com.previsit.app.user.request.LoginRequest;
import com.previsit.app.user.request.RegisterRequest;
import com.previsit.app.user.request.SendOtpRequest;
import com.previsit.app.user.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
  }

  @PostMapping("/send-otp")
  public ResponseEntity<ApiResponse> sendOtp(@Valid @RequestBody SendOtpRequest request) {
    return ResponseEntity.ok(authService.sendOtp(request));
  }

  @PostMapping("/login")
  public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }
}
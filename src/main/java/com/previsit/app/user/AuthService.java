package com.previsit.app.user;

import com.previsit.app.api.response.ApiResponse;
import com.previsit.app.user.request.LoginRequest;
import com.previsit.app.user.request.RegisterRequest;
import com.previsit.app.user.request.SendOtpRequest;
import com.previsit.app.user.response.UserResponse;

public interface AuthService {
  ApiResponse register(RegisterRequest request);
  ApiResponse sendOtp(SendOtpRequest request);
  UserResponse login(LoginRequest request);
}
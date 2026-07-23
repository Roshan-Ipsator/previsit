package com.previsit.app.user;

import com.previsit.app.api.response.ApiResponse;
import com.previsit.app.user.exception.InvalidOtpException;
import com.previsit.app.user.exception.UserAlreadyExistsException;
import com.previsit.app.user.exception.UserNotFoundException;
import com.previsit.app.user.request.LoginRequest;
import com.previsit.app.user.request.RegisterRequest;
import com.previsit.app.user.request.SendOtpRequest;
import com.previsit.app.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

  private static final String DUMMY_OTP = "1234";
  private final UserRepository userRepository;

  @Override
  public ApiResponse register(RegisterRequest request) {
    if (userRepository.existsByMobile(request.mobile())) {
      throw new UserAlreadyExistsException("Mobile number is already registered.");
    }
    if (userRepository.existsByEmail(request.email())) {
      throw new UserAlreadyExistsException("Email is already registered.");
    }
    User user = User.builder().name(request.name()).dateOfBirth(request.dateOfBirth())
        .gender(request.gender()).mobile(request.mobile()).email(request.email()).build();
    userRepository.save(user);
    return new ApiResponse(true, "Registration successful.");
  }

  @Override
  public ApiResponse sendOtp(SendOtpRequest request) {
    User user = userRepository.findByMobile(request.mobile())
        .orElseThrow(() -> new UserNotFoundException("User not found."));
    user.setOtp(DUMMY_OTP);
    userRepository.save(user);
    return new ApiResponse(true, "OTP generated successfully.");
  }

  @Override
  public UserResponse login(LoginRequest request) {
    User user = userRepository.findByMobile(request.mobile())
        .orElseThrow(() -> new UserNotFoundException("User not found."));
    if (!DUMMY_OTP.equals(user.getOtp()) || !request.otp().equals(user.getOtp())) {
      throw new InvalidOtpException("Invalid OTP.");
    }
    user.setOtp(null);
    userRepository.save(user);
    return new UserResponse(user.getId(), user.getName(), user.getDateOfBirth(), user.getGender(),
        user.getMobile(), user.getEmail());
  }
}
package com.previsit.app.user.exception;

import com.previsit.app.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ApiResponse> handleUserAlreadyExistsException(
      UserAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, ex.getMessage()));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiResponse(false, ex.getMessage()));
  }

  @ExceptionHandler(InvalidOtpException.class)
  public ResponseEntity<ApiResponse> handleInvalidOtpException(InvalidOtpException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ApiResponse(false, ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().stream().findFirst()
        .map(error -> error.getDefaultMessage()).orElse("Validation failed.");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, message));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ApiResponse(false, "Something went wrong."));
  }
}

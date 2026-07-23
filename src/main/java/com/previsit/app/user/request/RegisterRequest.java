package com.previsit.app.user.request;

import com.previsit.app.user.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record RegisterRequest(
    @NotBlank(message = "Name is required")
    String name,

    @NotNull(message = "Date of birth is required")
    LocalDate dateOfBirth,

    @NotNull(message = "Gender is required")
    Gender gender,

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    String mobile,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    String email
) {

}

package com.previsit.app.user.response;

import com.previsit.app.user.Gender;
import java.time.LocalDate;

public record UserResponse(
    Long id,
    String name,
    LocalDate dateOfBirth,
    Gender gender,
    String mobile,
    String email
) {

}
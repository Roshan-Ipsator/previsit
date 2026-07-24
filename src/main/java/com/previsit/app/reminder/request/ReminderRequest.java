package com.previsit.app.reminder.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReminderRequest(
    @NotBlank(message = "Event name is required")
    String eventName,

    @NotNull(message = "Event date and time is required")
    @Future(message = "Event date and time must be in the future")
    LocalDateTime eventDateTime,

    @NotNull(message = "Reminder date and time is required")
    LocalDateTime reminderDateTime
) {

}
package com.previsit.app.reminder.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateReminderRequest(
    @NotNull(message = "User Id is required")
    Long userId,

    @NotEmpty(message = "At least one reminder is required")
    @Valid
    List<ReminderRequest> reminders
) {

}

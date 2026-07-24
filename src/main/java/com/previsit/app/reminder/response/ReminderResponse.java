package com.previsit.app.reminder.response;

import java.time.LocalDateTime;

public record ReminderResponse(
    Long reminderId, String eventName, LocalDateTime eventDateTime,
    LocalDateTime reminderDateTime,
    Boolean emailSent
) {

}

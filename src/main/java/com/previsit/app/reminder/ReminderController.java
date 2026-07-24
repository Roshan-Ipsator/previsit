package com.previsit.app.reminder;

import com.previsit.app.api.response.ApiResponse;
import com.previsit.app.reminder.request.CreateReminderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reminders")
@RequiredArgsConstructor
public class ReminderController {

  private final ReminderService reminderService;

  @PostMapping
  public ResponseEntity<ApiResponse> createReminders(
      @Valid @RequestBody CreateReminderRequest request) {
    ApiResponse response = reminderService.createReminders(request);
    return ResponseEntity.ok(response);
  }
}
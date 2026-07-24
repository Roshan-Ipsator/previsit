package com.previsit.app.reminder;

import com.previsit.app.api.response.ApiResponse;
import com.previsit.app.reminder.request.CreateReminderRequest;

public interface ReminderService {

  ApiResponse createReminders(CreateReminderRequest request);

  void processPendingReminders();
}

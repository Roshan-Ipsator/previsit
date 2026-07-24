package com.previsit.app.scheduler;

import com.previsit.app.reminder.ReminderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderScheduler {

  private final ReminderService reminderService;

  /**
   * Runs every minute.
   */
  @Scheduled(cron = "0 * * * * *")
  public void processPendingReminders() {
    log.info("Reminder scheduler started.");
    try {
      reminderService.processPendingReminders();
      log.info("Reminder scheduler completed successfully.");
    } catch (Exception ex) {
      log.error("Error occurred while processing reminders.", ex);
    }
  }
}

package com.previsit.app.reminder;

import com.previsit.app.api.response.ApiResponse;
import com.previsit.app.email.EmailService;
import com.previsit.app.reminder.request.CreateReminderRequest;
import com.previsit.app.reminder.request.ReminderRequest;
import com.previsit.app.user.User;
import com.previsit.app.user.UserRepository;
import com.previsit.app.user.exception.UserNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
      "dd MMM yyyy hh:mm a");

  private final EventReminderRepository eventReminderRepository;
  private final UserRepository userRepository;
  private final EmailService emailService;

  @Override
  @Transactional
  public ApiResponse createReminders(CreateReminderRequest request) {
    try {
      User user = userRepository.findById(request.userId())
          .orElseThrow(() -> new UserNotFoundException("User not found."));
      List<EventReminder> reminders = new ArrayList<>();
      for (ReminderRequest reminderRequest : request.reminders()) {
        EventReminder reminder = EventReminder.builder().user(user)
            .eventName(reminderRequest.eventName()).eventDateTime(reminderRequest.eventDateTime())
            .reminderDateTime(reminderRequest.reminderDateTime()).build();
        reminders.add(reminder);
      }
      eventReminderRepository.saveAll(reminders);
      return new ApiResponse(true, "Reminders created successfully.");
    } catch (Exception e){
      log.info(e.getMessage());
      return new ApiResponse(false, e.getMessage());
    }
  }

  @Override
  @Transactional
  public void processPendingReminders() {
    List<EventReminder> reminders = eventReminderRepository.findByEmailSentFalseAndReminderDateTimeLessThanEqual(
        java.time.LocalDateTime.now());
    if (reminders.isEmpty()) {
      return;
    }
    log.info("Found {} pending reminders.", reminders.size());
    for (EventReminder reminder : reminders) {
      try {
        String subject = "Reminder: " + reminder.getEventName();
        String body = buildEmailBody(reminder);

        log.info("receiver email: "+reminder.getUser().getEmail());
        log.info("subject: "+subject);
        log.info("body: "+body);

        emailService.sendEmail(reminder.getUser().getEmail(), subject, body);
        reminder.setEmailSent(true);
        eventReminderRepository.save(reminder);
        log.info("Reminder email sent successfully. Reminder Id={}", reminder.getId());
      } catch (Exception ex) {
        log.error("Failed to send reminder email for Reminder Id={}", reminder.getId(), ex);
      }
    }
  }

  private String buildEmailBody(EventReminder reminder) {
    return ("Hello %s, This is a reminder for your upcoming event. Event Name: %s Event Date & Time: %s Regards, PreVisit").formatted(
        reminder.getUser().getName(), reminder.getEventName(),
        reminder.getEventDateTime().format(FORMATTER));
  }
}

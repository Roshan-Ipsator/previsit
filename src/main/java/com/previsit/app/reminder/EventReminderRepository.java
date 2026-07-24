package com.previsit.app.reminder;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventReminderRepository extends JpaRepository<EventReminder, Long> {

  List<EventReminder> findByUserId(Long userId);

  List<EventReminder> findByEmailSentFalseAndReminderDateTimeLessThanEqual(
      LocalDateTime reminderDateTime);
}

package com.previsit.app.reminder;

import com.previsit.app.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_reminders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventReminder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String eventName;

  @Column(nullable = false)
  private LocalDateTime eventDateTime;

  @Column(nullable = false)
  private LocalDateTime reminderDateTime;

  @Column(nullable = false)
  @Builder.Default
  private Boolean emailSent = false;

}
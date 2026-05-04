package br.com.hms.dto.queue;

import br.com.hms.domain.enums.QueueStatus;
import br.com.hms.domain.enums.UrgencyLevel;
import java.time.LocalDateTime;

public record QueueTicketResponse(
    Long id,
    Long patientId,
    String patientName,
    LocalDateTime arrivedAt,
    LocalDateTime calledAt,
    LocalDateTime attendedAt,
    UrgencyLevel urgencyLevel,
    QueueStatus status,
    String counter,
    LocalDateTime createdAt
) {}

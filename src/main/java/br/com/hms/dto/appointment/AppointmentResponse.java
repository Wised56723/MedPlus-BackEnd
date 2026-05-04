package br.com.hms.dto.appointment;

import br.com.hms.domain.enums.AppointmentStatus;
import java.time.LocalDateTime;

public record AppointmentResponse(
    Long id,
    Long patientId,
    String patientName,
    Long doctorId,
    String doctorName,
    LocalDateTime scheduledAt,
    AppointmentStatus status,
    String specialty,
    String notes,
    LocalDateTime createdAt
) {}

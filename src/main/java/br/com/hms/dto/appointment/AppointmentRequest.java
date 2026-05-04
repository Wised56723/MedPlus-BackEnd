package br.com.hms.dto.appointment;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentRequest(
    @NotNull Long patientId,
    @NotNull Long doctorId,
    @NotNull LocalDateTime scheduledAt,
    String specialty,
    String notes
) {}

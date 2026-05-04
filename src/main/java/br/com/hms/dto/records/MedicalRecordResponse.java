package br.com.hms.dto.records;

import java.time.LocalDateTime;

public record MedicalRecordResponse(
    Long id,
    Long patientId,
    String patientName,
    Long doctorId,
    String doctorName,
    Long appointmentId,
    String diagnosis,
    String prescription,
    String notes,
    LocalDateTime createdAt
) {}

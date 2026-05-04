package br.com.hms.dto.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientResponse(
    Long id,
    String fullName,
    String cpf,
    LocalDate dateOfBirth,
    String phone,
    String email,
    String address,
    String bloodType,
    String allergies,
    String insurancePlan,
    LocalDateTime createdAt
) {}

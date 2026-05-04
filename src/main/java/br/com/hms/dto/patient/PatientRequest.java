package br.com.hms.dto.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PatientRequest(
    @NotBlank String fullName,
    @NotBlank String cpf,
    @NotNull  LocalDate dateOfBirth,
    String phone,
    String email,
    String address,
    String bloodType,
    String allergies,
    String insurancePlan
) {}

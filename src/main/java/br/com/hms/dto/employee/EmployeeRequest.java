package br.com.hms.dto.employee;

import br.com.hms.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeRequest(
    @NotBlank String fullName,
    @NotBlank String cpf,
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotNull  Role role,
    String specialty,
    String crm
) {}

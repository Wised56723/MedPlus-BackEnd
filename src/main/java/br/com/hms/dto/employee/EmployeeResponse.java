package br.com.hms.dto.employee;

import br.com.hms.domain.enums.Role;
import java.time.LocalDateTime;

public record EmployeeResponse(
    Long id,
    String fullName,
    String cpf,
    String email,
    Role role,
    String specialty,
    String crm,
    Boolean active,
    LocalDateTime createdAt
) {}

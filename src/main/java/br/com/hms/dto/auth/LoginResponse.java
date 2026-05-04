package br.com.hms.dto.auth;

import br.com.hms.domain.enums.Role;

public record LoginResponse(
    String token,
    Long employeeId,
    String fullName,
    String email,
    Role role
) {}

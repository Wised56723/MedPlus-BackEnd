package br.com.hms.service;

import br.com.hms.domain.entity.Employee;
import br.com.hms.dto.auth.LoginRequest;
import br.com.hms.dto.auth.LoginResponse;
import br.com.hms.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        Employee employee = (Employee) auth.getPrincipal();
        String token = jwtService.generateToken(employee);

        return new LoginResponse(
                token,
                employee.getId(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getRole());
    }
}

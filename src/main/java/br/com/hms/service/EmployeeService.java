package br.com.hms.service;

import br.com.hms.domain.entity.Employee;
import br.com.hms.domain.repository.EmployeeRepository;
import br.com.hms.dto.employee.EmployeeRequest;
import br.com.hms.dto.employee.EmployeeResponse;
import br.com.hms.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    public EmployeeResponse findById(Long id) {
        return employeeMapper.toResponse(getOrThrow(id));
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        if (employeeRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "E-mail já cadastrado: " + request.email());
        }
        Employee employee = employeeMapper.toEntity(request);
        employee.setPasswordHash(passwordEncoder.encode(request.password()));
        employee.setActive(true);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = getOrThrow(id);
        employeeMapper.updateEntity(request, employee);
        if (request.password() != null && !request.password().isBlank()) {
            employee.setPasswordHash(passwordEncoder.encode(request.password()));
        }
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Transactional
    public void deactivate(Long id) {
        Employee employee = getOrThrow(id);
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    private Employee getOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Funcionário não encontrado: " + id));
    }
}

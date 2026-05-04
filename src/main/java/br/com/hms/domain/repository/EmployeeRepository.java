package br.com.hms.domain.repository;

import br.com.hms.domain.entity.Employee;
import br.com.hms.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    List<Employee> findByRoleAndActiveTrue(Role role);
    List<Employee> findByActiveTrue();
}

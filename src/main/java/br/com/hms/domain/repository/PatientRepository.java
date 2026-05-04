package br.com.hms.domain.repository;

import br.com.hms.domain.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByCpf(String cpf);
    boolean existsByCpf(String cpf);

    @Query("SELECT p FROM Patient p WHERE " +
           "LOWER(p.fullName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "p.cpf LIKE CONCAT('%', :q, '%')")
    Page<Patient> search(@Param("q") String query, Pageable pageable);
}

package br.com.hms.domain.repository;

import br.com.hms.domain.entity.Appointment;
import br.com.hms.domain.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorIdAndScheduledAtBetween(
            Long doctorId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
           "AND a.scheduledAt BETWEEN :start AND :end " +
           "AND a.status <> :cancelled")
    List<Appointment> findConflicts(@Param("doctorId") Long doctorId,
                                    @Param("start") LocalDateTime start,
                                    @Param("end") LocalDateTime end,
                                    @Param("cancelled") AppointmentStatus cancelled);
}

package br.com.hms.service;

import br.com.hms.domain.entity.Appointment;
import br.com.hms.domain.entity.Employee;
import br.com.hms.domain.entity.Patient;
import br.com.hms.domain.enums.AppointmentStatus;
import br.com.hms.domain.repository.AppointmentRepository;
import br.com.hms.domain.repository.EmployeeRepository;
import br.com.hms.domain.repository.PatientRepository;
import br.com.hms.dto.appointment.AppointmentRequest;
import br.com.hms.dto.appointment.AppointmentResponse;
import br.com.hms.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    private final AppointmentMapper appointmentMapper;

    public List<AppointmentResponse> list(Long doctorId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = date.plusDays(1).atStartOfDay();
        return appointmentRepository
                .findByDoctorIdAndScheduledAtBetween(doctorId, start, end)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    public List<AppointmentResponse> findByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Transactional
    public AppointmentResponse create(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente não encontrado: " + request.patientId()));

        Employee doctor = employeeRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Médico não encontrado: " + request.doctorId()));

        // Conflict: 60-min window around the requested time
        LocalDateTime reqTime = request.scheduledAt();
        List<Appointment> conflicts = appointmentRepository.findConflicts(
                request.doctorId(),
                reqTime.minusMinutes(59),
                reqTime.plusMinutes(59),
                AppointmentStatus.CANCELLED);

        if (!conflicts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Médico já possui consulta neste horário.");
        }

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .scheduledAt(request.scheduledAt())
                .specialty(request.specialty())
                .notes(request.notes())
                .status(AppointmentStatus.SCHEDULED)
                .build();

        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment appointment = getOrThrow(id);

        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente não encontrado: " + request.patientId()));

        Employee doctor = employeeRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Médico não encontrado: " + request.doctorId()));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setScheduledAt(request.scheduledAt());
        appointment.setSpecialty(request.specialty());
        appointment.setNotes(request.notes());

        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public void cancel(Long id) {
        Appointment appointment = getOrThrow(id);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    private Appointment getOrThrow(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Consulta não encontrada: " + id));
    }
}

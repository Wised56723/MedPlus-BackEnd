package br.com.hms.service;

import br.com.hms.domain.entity.Appointment;
import br.com.hms.domain.entity.Employee;
import br.com.hms.domain.entity.MedicalRecord;
import br.com.hms.domain.entity.Patient;
import br.com.hms.domain.repository.AppointmentRepository;
import br.com.hms.domain.repository.EmployeeRepository;
import br.com.hms.domain.repository.MedicalRecordRepository;
import br.com.hms.domain.repository.PatientRepository;
import br.com.hms.dto.records.MedicalRecordRequest;
import br.com.hms.dto.records.MedicalRecordResponse;
import br.com.hms.mapper.MedicalRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    public List<MedicalRecordResponse> findByPatient(Long patientId) {
        return medicalRecordRepository.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(medicalRecordMapper::toResponse)
                .toList();
    }

    @Transactional
    public MedicalRecordResponse create(MedicalRecordRequest request, Long doctorId) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente não encontrado: " + request.patientId()));

        Employee doctor = employeeRepository.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Médico não encontrado: " + doctorId));

        Appointment appointment = null;
        if (request.appointmentId() != null) {
            appointment = appointmentRepository.findById(request.appointmentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Consulta não encontrada: " + request.appointmentId()));
        }

        MedicalRecord record = MedicalRecord.builder()
                .patient(patient)
                .doctor(doctor)
                .appointment(appointment)
                .diagnosis(request.diagnosis())
                .prescription(request.prescription())
                .notes(request.notes())
                .build();

        return medicalRecordMapper.toResponse(medicalRecordRepository.save(record));
    }
}

package br.com.hms.controller;

import br.com.hms.dto.patient.PatientRequest;
import br.com.hms.dto.patient.PatientResponse;
import br.com.hms.dto.records.MedicalRecordResponse;
import br.com.hms.service.MedicalRecordService;
import br.com.hms.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final MedicalRecordService medicalRecordService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','ENFERMEIRO','RECEPCIONISTA')")
    public ResponseEntity<Page<PatientResponse>> search(
            @RequestParam(required = false) String q,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(patientService.search(q, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','ENFERMEIRO','RECEPCIONISTA')")
    public ResponseEntity<PatientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','RECEPCIONISTA')")
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','RECEPCIONISTA')")
    public ResponseEntity<PatientResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.update(id, request));
    }

    @GetMapping("/{id}/records")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','ENFERMEIRO')")
    public ResponseEntity<List<MedicalRecordResponse>> getMedicalRecords(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.findByPatient(id));
    }
}

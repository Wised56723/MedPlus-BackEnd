package br.com.hms.controller;

import br.com.hms.domain.entity.Employee;
import br.com.hms.dto.records.MedicalRecordRequest;
import br.com.hms.dto.records.MedicalRecordResponse;
import br.com.hms.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ResponseEntity<MedicalRecordResponse> create(
            @Valid @RequestBody MedicalRecordRequest request,
            @AuthenticationPrincipal Employee currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicalRecordService.create(request, currentUser.getId()));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','ENFERMEIRO')")
    public ResponseEntity<List<MedicalRecordResponse>> findByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalRecordService.findByPatient(patientId));
    }
}

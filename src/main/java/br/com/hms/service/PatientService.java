package br.com.hms.service;

import br.com.hms.domain.entity.Patient;
import br.com.hms.domain.repository.PatientRepository;
import br.com.hms.dto.patient.PatientRequest;
import br.com.hms.dto.patient.PatientResponse;
import br.com.hms.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public Page<PatientResponse> search(String query, Pageable pageable) {
        return patientRepository.search(query == null ? "" : query, pageable)
                .map(patientMapper::toResponse);
    }

    public PatientResponse findById(Long id) {
        return patientMapper.toResponse(getOrThrow(id));
    }

    @Transactional
    public PatientResponse create(PatientRequest request) {
        if (patientRepository.existsByCpf(request.cpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "CPF já cadastrado: " + request.cpf());
        }
        Patient patient = patientMapper.toEntity(request);
        return patientMapper.toResponse(patientRepository.save(patient));
    }

    @Transactional
    public PatientResponse update(Long id, PatientRequest request) {
        Patient patient = getOrThrow(id);
        patientMapper.updateEntity(request, patient);
        return patientMapper.toResponse(patientRepository.save(patient));
    }

    private Patient getOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente não encontrado: " + id));
    }
}

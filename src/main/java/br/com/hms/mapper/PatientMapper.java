package br.com.hms.mapper;

import br.com.hms.domain.entity.Patient;
import br.com.hms.dto.patient.PatientRequest;
import br.com.hms.dto.patient.PatientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(PatientRequest request);

    PatientResponse toResponse(Patient patient);

    void updateEntity(PatientRequest request, @MappingTarget Patient patient);
}

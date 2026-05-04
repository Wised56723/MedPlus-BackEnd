package br.com.hms.mapper;

import br.com.hms.domain.entity.MedicalRecord;
import br.com.hms.dto.records.MedicalRecordResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(target = "patientId",      source = "patient.id")
    @Mapping(target = "patientName",    source = "patient.fullName")
    @Mapping(target = "doctorId",       source = "doctor.id")
    @Mapping(target = "doctorName",     source = "doctor.fullName")
    @Mapping(target = "appointmentId",  source = "appointment.id")
    MedicalRecordResponse toResponse(MedicalRecord record);
}

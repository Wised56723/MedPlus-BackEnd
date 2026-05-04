package br.com.hms.mapper;

import br.com.hms.domain.entity.Appointment;
import br.com.hms.dto.appointment.AppointmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "patientId",   source = "patient.id")
    @Mapping(target = "patientName", source = "patient.fullName")
    @Mapping(target = "doctorId",    source = "doctor.id")
    @Mapping(target = "doctorName",  source = "doctor.fullName")
    AppointmentResponse toResponse(Appointment appointment);
}

package br.com.hms.mapper;

import br.com.hms.domain.entity.QueueTicket;
import br.com.hms.dto.queue.QueueTicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QueueTicketMapper {

    @Mapping(target = "patientId",   source = "patient.id")
    @Mapping(target = "patientName", source = "patient.fullName")
    QueueTicketResponse toResponse(QueueTicket ticket);
}

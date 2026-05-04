package br.com.hms.dto.queue;

import br.com.hms.domain.enums.UrgencyLevel;
import jakarta.validation.constraints.NotNull;

public record QueueTicketRequest(
    @NotNull Long patientId,
    UrgencyLevel urgencyLevel
) {}

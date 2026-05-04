package br.com.hms.service;

import br.com.hms.domain.entity.Patient;
import br.com.hms.domain.entity.QueueTicket;
import br.com.hms.domain.enums.QueueStatus;
import br.com.hms.domain.enums.UrgencyLevel;
import br.com.hms.domain.repository.PatientRepository;
import br.com.hms.domain.repository.QueueTicketRepository;
import br.com.hms.dto.queue.QueueTicketRequest;
import br.com.hms.dto.queue.QueueTicketResponse;
import br.com.hms.mapper.QueueTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueService {

    private static final String QUEUE_TOPIC = "/topic/queue";

    private final QueueTicketRepository queueTicketRepository;
    private final PatientRepository patientRepository;
    private final QueueTicketMapper queueTicketMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public List<QueueTicketResponse> getActiveTickets() {
        return queueTicketRepository.findActiveTickets()
                .stream()
                .map(queueTicketMapper::toResponse)
                .toList();
    }

    @Transactional
    public QueueTicketResponse issueTicket(QueueTicketRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente não encontrado: " + request.patientId()));

        UrgencyLevel urgency = request.urgencyLevel() != null
                ? request.urgencyLevel()
                : UrgencyLevel.GREEN;

        long waiting = queueTicketRepository.countByStatus(QueueStatus.WAITING);
        String counter = generateCounter(urgency, waiting + 1);

        QueueTicket ticket = QueueTicket.builder()
                .patient(patient)
                .urgencyLevel(urgency)
                .status(QueueStatus.WAITING)
                .arrivedAt(LocalDateTime.now())
                .counter(counter)
                .build();

        QueueTicket saved = queueTicketRepository.save(ticket);
        QueueTicketResponse response = queueTicketMapper.toResponse(saved);
        messagingTemplate.convertAndSend(QUEUE_TOPIC, response);
        return response;
    }

    @Transactional
    public QueueTicketResponse callTicket(Long ticketId) {
        QueueTicket ticket = getOrThrow(ticketId);
        ticket.setStatus(QueueStatus.CALLED);
        ticket.setCalledAt(LocalDateTime.now());
        QueueTicketResponse response = queueTicketMapper.toResponse(queueTicketRepository.save(ticket));
        messagingTemplate.convertAndSend(QUEUE_TOPIC, response);
        return response;
    }

    @Transactional
    public QueueTicketResponse attendTicket(Long ticketId) {
        QueueTicket ticket = getOrThrow(ticketId);
        ticket.setStatus(QueueStatus.ATTENDING);
        ticket.setAttendedAt(LocalDateTime.now());
        QueueTicketResponse response = queueTicketMapper.toResponse(queueTicketRepository.save(ticket));
        messagingTemplate.convertAndSend(QUEUE_TOPIC, response);
        return response;
    }

    private QueueTicket getOrThrow(Long id) {
        return queueTicketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ficha não encontrada: " + id));
    }

    private String generateCounter(UrgencyLevel urgency, long seq) {
        String prefix = switch (urgency) {
            case RED    -> "V";   // Vermelho
            case ORANGE -> "L";   // Laranja
            case YELLOW -> "A";   // Amarelo
            case GREEN  -> "V";   // Verde
            case BLUE   -> "A";   // Azul
        };
        return prefix + String.format("%03d", seq);
    }
}

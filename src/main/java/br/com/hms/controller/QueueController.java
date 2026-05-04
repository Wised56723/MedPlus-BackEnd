package br.com.hms.controller;

import br.com.hms.dto.queue.QueueTicketRequest;
import br.com.hms.dto.queue.QueueTicketResponse;
import br.com.hms.service.QueueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','ENFERMEIRO','RECEPCIONISTA','FARMACEUTICO')")
    public ResponseEntity<List<QueueTicketResponse>> getActive() {
        return ResponseEntity.ok(queueService.getActiveTickets());
    }

    @PostMapping("/tickets")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
    public ResponseEntity<QueueTicketResponse> issue(@Valid @RequestBody QueueTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(queueService.issueTicket(request));
    }

    @PutMapping("/tickets/{id}/call")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','ENFERMEIRO')")
    public ResponseEntity<QueueTicketResponse> call(@PathVariable Long id) {
        return ResponseEntity.ok(queueService.callTicket(id));
    }

    @PutMapping("/tickets/{id}/attend")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ResponseEntity<QueueTicketResponse> attend(@PathVariable Long id) {
        return ResponseEntity.ok(queueService.attendTicket(id));
    }
}

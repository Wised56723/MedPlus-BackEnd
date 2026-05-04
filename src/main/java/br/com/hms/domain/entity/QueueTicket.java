package br.com.hms.domain.entity;

import br.com.hms.domain.enums.QueueStatus;
import br.com.hms.domain.enums.UrgencyLevel;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue_tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QueueTicket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "arrived_at", nullable = false)
    private LocalDateTime arrivedAt;

    @Column(name = "called_at")
    private LocalDateTime calledAt;

    @Column(name = "attended_at")
    private LocalDateTime attendedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "urgency_level", nullable = false, length = 20)
    private UrgencyLevel urgencyLevel = UrgencyLevel.GREEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QueueStatus status = QueueStatus.WAITING;

    @Column(length = 10)
    private String counter;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (arrivedAt == null) arrivedAt = LocalDateTime.now();
    }
}

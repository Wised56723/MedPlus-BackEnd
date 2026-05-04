package br.com.hms.domain.repository;

import br.com.hms.domain.entity.QueueTicket;
import br.com.hms.domain.enums.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface QueueTicketRepository extends JpaRepository<QueueTicket, Long> {

    List<QueueTicket> findByStatusOrderByUrgencyLevelAscArrivedAtAsc(QueueStatus status);

    @Query("SELECT t FROM QueueTicket t WHERE t.status IN ('WAITING','CALLED','ATTENDING') " +
           "ORDER BY t.urgencyLevel ASC, t.arrivedAt ASC")
    List<QueueTicket> findActiveTickets();

    long countByStatus(QueueStatus status);
}

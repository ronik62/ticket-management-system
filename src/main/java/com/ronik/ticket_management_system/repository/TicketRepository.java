package com.ronik.ticket_management_system.repository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.ronik.ticket_management_system.entity.Ticket;
//import com.ronik.ticket_management_system.enums.Priority;
//import com.ronik.ticket_management_system.enums.Status;

public interface TicketRepository extends JpaRepository<Ticket, Long>,JpaSpecificationExecutor<Ticket> {
    

    //implemented specification so now we dont need this methods
    // Page<Ticket> findByStatus(Status status,Pageable pageable);

    // Page<Ticket> findByPriority(Priority priority,Pageable pageable);

    // Page<Ticket> findByRequesterName(String requesterName,Pageable pageable);

    // Page<Ticket> findByStatusAndPriority(Status status, Priority priority,Pageable pageable);

}

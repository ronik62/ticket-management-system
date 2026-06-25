package com.ronik.ticket_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ronik.ticket_management_system.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}

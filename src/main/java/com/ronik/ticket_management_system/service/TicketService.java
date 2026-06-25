package com.ronik.ticket_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ronik.ticket_management_system.entity.Ticket;
import com.ronik.ticket_management_system.repository.TicketRepository;

@Service
public class TicketService {
    
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // create a method to save a ticket
    public Ticket createTicket(Ticket ticket){
        
        ticket.setStatus("OPEN");
        ticket.setTeamAssigned("Service Desk");
        ticket.setCreatedAt(java.time.LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    // create a method to get all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}

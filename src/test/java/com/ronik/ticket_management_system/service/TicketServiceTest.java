package com.ronik.ticket_management_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ronik.ticket_management_system.dto.TicketRequestDTO;
import com.ronik.ticket_management_system.dto.TicketResponseDTO;
import com.ronik.ticket_management_system.entity.Ticket;
import com.ronik.ticket_management_system.enums.Priority;
import com.ronik.ticket_management_system.enums.Status;
import com.ronik.ticket_management_system.exception.TicketNotFoundException;
import com.ronik.ticket_management_system.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)

public class TicketServiceTest {

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;
    
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void shouldCreateTicketSuccessfully(){

        //as we are not getting spring boot application context we need to manually create requestdto 
        TicketRequestDTO requestDTO = new TicketRequestDTO();
        
        requestDTO.setRequesterName("Ronik");
        requestDTO.setSubject("VPN Issue");
        requestDTO.setDescription("Unable to connect");
        requestDTO.setPriority(Priority.P1);

        Ticket savedTicket = new Ticket();

        savedTicket.setId(1L);
        savedTicket.setRequesterName("Ronik");
        savedTicket.setSubject("VPN Issue");
        savedTicket.setDescription("Unable to connect");
        savedTicket.setPriority(Priority.P1);
        savedTicket.setStatus(Status.OPEN);
        savedTicket.setTeamAssigned("Service Desk");
        savedTicket.setCreatedAt(LocalDateTime.now());

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        TicketResponseDTO responseDTO = ticketService.createTicket(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L,responseDTO.getId());
        assertEquals("Ronik",responseDTO.getRequesterName());
        assertEquals("VPN Issue",responseDTO.getSubject());
        assertEquals(Priority.P1,responseDTO.getPriority());
        assertEquals(Status.OPEN,responseDTO.getStatus());
        assertNotNull(responseDTO.getCreatedAt());

        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void shouldAssignDefaultPriorityWhenPriorityIsNull(){
        
        TicketRequestDTO requestDTO = new TicketRequestDTO();

        requestDTO.setRequesterName("Ronik");
        requestDTO.setSubject("VPN Issue");
        requestDTO.setDescription("Unable to connect");

        Ticket savedTicket = new Ticket();

        savedTicket.setId(1L);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        ticketService.createTicket(requestDTO);

        verify(ticketRepository).save(ticketCaptor.capture());

        assertEquals(Priority.P3,ticketCaptor.getValue().getPriority());
        
    }

    @Test
    void shouldReturnTicketWhenTicketExists(){

        Ticket ticket = new Ticket();

        ticket.setId(1L);
        ticket.setRequesterName("Ronik");
        ticket.setSubject("VPN Issue");
        ticket.setPriority(Priority.P3);
        ticket.setStatus(Status.OPEN);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicketById(1L);

        assertNotNull(result);
        assertEquals(1L,result.getId());
        assertEquals("Ronik",result.getRequesterName());
        assertEquals("VPN Issue",result.getSubject());
        assertEquals(Priority.P3,result.getPriority());
        assertEquals(Status.OPEN,result.getStatus());

        verify(ticketRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenTicketDoesNotExist(){

        when(ticketRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class,()-> ticketService.getTicketById(100L));

        verify(ticketRepository).findById(100L);
    }

    @Test
    void shouldDeleteTicketSuccessfully(){
        Ticket ticket = new Ticket();

        ticket.setId(1L);
        ticket.setRequesterName("Ronik");
        ticket.setSubject("VPN Issue");
        ticket.setPriority(Priority.P1);
        ticket.setStatus(Status.OPEN);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        ticketService.deleteTicket(1L);

        verify(ticketRepository).findById(1L);
        verify(ticketRepository).delete(ticket);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTicket(){

        when(ticketRepository.findById(100L)).thenReturn(Optional.empty());

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class,()-> ticketService.deleteTicket(100L));

        assertEquals("Ticket not found with id: 100", exception.getMessage());

        verify(ticketRepository).findById(100L);

        verify(ticketRepository,never()).delete(any(Ticket.class));
    }
}

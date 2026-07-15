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

    /*
        * Helper methods to create reusable test objects.
 *
        * Why?
        * Earlier, every test was creating the same Ticket and TicketRequestDTO
        * objects using multiple setter calls. This resulted in duplicated code.
        *
        * Instead of repeating the same object creation in every test,
        * we create it once here and reuse it.
        *
        * This makes tests:
        * 1. Shorter
        * 2. Easier to read
        * 3. Easier to maintain
        *
        * If a default value changes in future,
        * we only update it in one place.
    */

    private Ticket createExistingTicket() {

        Ticket ticket = new Ticket();

        ticket.setId(1L);
        ticket.setRequesterName("Ronik");
        ticket.setSubject("VPN Issue");
        ticket.setDescription("Unable to connect");
        ticket.setPriority(Priority.P3);
        ticket.setStatus(Status.OPEN);
        ticket.setTeamAssigned("Service Desk");

        return ticket;
    }

    private Ticket createUpdatedTicket() {

        Ticket ticket = new Ticket();

        ticket.setPriority(Priority.P1);
        ticket.setStatus(Status.CLOSED);
        ticket.setTeamAssigned("L2 Network");

        return ticket;
    }

    private TicketRequestDTO createTicketRequestDTO() {

        TicketRequestDTO requestDTO = createRequestWithoutPriority();
        requestDTO.setPriority(Priority.P1);

        return requestDTO;
    }


    private TicketRequestDTO createRequestWithoutPriority() {

        TicketRequestDTO requestDTO = new TicketRequestDTO();

        requestDTO.setRequesterName("Ronik");
        requestDTO.setSubject("VPN Issue");
        requestDTO.setDescription("Unable to connect");

        return requestDTO;
    }

    private Ticket createSavedTicket() {

        Ticket ticket = new Ticket();

        ticket.setId(1L);
        ticket.setRequesterName("Ronik");
        ticket.setSubject("VPN Issue");
        ticket.setDescription("Unable to connect");
        ticket.setPriority(Priority.P1);
        ticket.setStatus(Status.OPEN);
        ticket.setTeamAssigned("Service Desk");
        ticket.setCreatedAt(LocalDateTime.now());

        return ticket;
    }


    @Test
    void shouldCreateTicketSuccessfully(){

        //as we are not getting spring boot application context we need to manually create requestdto 
        TicketRequestDTO requestDTO = createTicketRequestDTO();

        Ticket savedTicket = createSavedTicket();

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
        
        TicketRequestDTO requestDTO = createRequestWithoutPriority();

        Ticket savedTicket = new Ticket();

        savedTicket.setId(1L);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        ticketService.createTicket(requestDTO);

        verify(ticketRepository).save(ticketCaptor.capture());

        assertEquals(Priority.P3,ticketCaptor.getValue().getPriority());
        
    }

    @Test
    void shouldReturnTicketWhenTicketExists(){

        Ticket ticket = createExistingTicket();

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
        Ticket ticket = createExistingTicket();
        ticket.setPriority(Priority.P1);

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

    @Test
    void shouldUpdateTicketSuccessfully(){

        Ticket existingTicket = createExistingTicket();

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(existingTicket));

        Ticket updatedTicket = createUpdatedTicket();

        ticketService.updateTicket(1L, updatedTicket);

        verify(ticketRepository).findById(1L);

        verify(ticketRepository).save(ticketCaptor.capture());

        Ticket capturedTicket = ticketCaptor.getValue();

        assertEquals(Priority.P1, capturedTicket.getPriority());
        assertEquals(Status.CLOSED,capturedTicket.getStatus());
        assertEquals("L2 Network",capturedTicket.getTeamAssigned());
        assertEquals("Ronik",capturedTicket.getRequesterName());
        assertEquals("VPN Issue",capturedTicket.getSubject());
        assertEquals("Unable to connect", capturedTicket.getDescription());

    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingTicket(){

        when(ticketRepository.findById(100L)).thenReturn(Optional.empty());

        Ticket updatedTicket = new Ticket();

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, ()->ticketService.updateTicket(100L, updatedTicket));

        assertEquals("Ticket not found by id 100", exception.getMessage());

        verify(ticketRepository).findById(100L);

        verify(ticketRepository,never()).save(any(Ticket.class));
    }



}

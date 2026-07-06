package com.ronik.ticket_management_system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ronik.ticket_management_system.dto.TicketRequestDTO;
import com.ronik.ticket_management_system.dto.TicketResponseDTO;
import com.ronik.ticket_management_system.entity.Ticket;
import com.ronik.ticket_management_system.enums.Priority;
import com.ronik.ticket_management_system.enums.Status;
import com.ronik.ticket_management_system.exception.TicketNotFoundException;
import com.ronik.ticket_management_system.repository.TicketRepository;
import com.ronik.ticket_management_system.specifications.TicketSpecification;

@Service
public class TicketService {
    
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // helper method
    private TicketResponseDTO mapToResponseDTO(Ticket ticket){
        TicketResponseDTO responseDTO = new TicketResponseDTO();
        responseDTO.setId(ticket.getId());
        responseDTO.setRequesterName(ticket.getRequesterName());
        responseDTO.setSubject(ticket.getSubject());
        responseDTO.setPriority(ticket.getPriority());
        responseDTO.setStatus(ticket.getStatus());
        responseDTO.setCreatedAt(ticket.getCreatedAt());
        
        return responseDTO;
    }

    private List<TicketResponseDTO> mapToResponseDTOList(List<Ticket> tickets){
        List<TicketResponseDTO> responseDTOs = new ArrayList<>();

        for(Ticket ticket: tickets){
            responseDTOs.add(mapToResponseDTO(ticket));
        }

        return responseDTOs;
    }

    // create a method to save a ticket
    public TicketResponseDTO createTicket(TicketRequestDTO requestDTO){

        Ticket ticket = new Ticket();

        ticket.setRequesterName(requestDTO.getRequesterName());
        ticket.setSubject(requestDTO.getSubject());
        ticket.setDescription(requestDTO.getDescription());
        if(requestDTO.getPriority()==null){
            ticket.setPriority(Priority.P3);
        }else{
            ticket.setPriority(requestDTO.getPriority());
        }
        ticket.setStatus(Status.OPEN);
        ticket.setCreatedAt(java.time.LocalDateTime.now());
        ticket.setTeamAssigned("Service Desk");
        Ticket savedTicket = ticketRepository.save(ticket);

        return mapToResponseDTO(savedTicket);
    }

    // create a method to get all tickets
    public Page<TicketResponseDTO> getAllTickets(int page, int size, Priority priority, Status status,String requesterName,String sortBy,String direction) {

        Set<String> allowedFields = Set.of("createdAt","priority","status","requesterName");

        if(!allowedFields.contains(sortBy)){
            throw new IllegalArgumentException("Invalid sort field : " + sortBy);
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);

        Sort sort = Sort.by(sortDirection,sortBy);

        Pageable pageable = PageRequest.of(page,size,sort);

        Specification<Ticket> specification = Specification.unrestricted();

        if(status != null){
            specification = specification.and(TicketSpecification.hasStatus(status));
        }
        if(priority != null){
            specification = specification.and(TicketSpecification.hasPriority(priority));
        }
        if(requesterName != null && !requesterName.isBlank()){
            specification = specification.and(TicketSpecification.hasRequesterName(requesterName));
        }

        return ticketRepository.findAll(specification,pageable).map(this::mapToResponseDTO);
        //using specification to refactor this below if else code 
        // if(priority == null && status == null ){
        //     return ticketRepository.findAll(pageable).map(this::mapToResponseDTO);
        // }else if(priority != null && status !=null){
        //     return ticketRepository.findByStatusAndPriority(status, priority, pageable).map(this::mapToResponseDTO);
        // }else if(priority == null && status != null){
        //     return ticketRepository.findByStatus(status, pageable).map(this::mapToResponseDTO);
        // }else{
            
        //     return ticketRepository.findByPriority(priority,pageable).map(this::mapToResponseDTO);
        // }
    }

    public Ticket getTicketById(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if(ticket.isPresent()){
            return ticket.get();
        }else{
            throw new TicketNotFoundException("Ticket not found with id " + id);
        }
        
    }

	public Ticket updateTicket(Long id,Ticket ticket) {
		Optional<Ticket> existingTicket = ticketRepository.findById(id);
        if(existingTicket.isPresent()){
            Ticket dbTicket = existingTicket.get();
            dbTicket.setPriority(ticket.getPriority());
            dbTicket.setStatus(ticket.getStatus());
            dbTicket.setTeamAssigned(ticket.getTeamAssigned());
            ticketRepository.save(dbTicket);
            return dbTicket;
        }else{
            throw new TicketNotFoundException("Ticket not found by id "+ id);
        }
	}

    // refactored the code using specifications so we dont need this methods
    // public Page<TicketResponseDTO> findByRequesterName(String requesterName){

    //     return ticketRepository.findByRequesterName(requesterName, null).map(this::mapToResponseDTO);
    // }

    // public Page<TicketResponseDTO> findByStatus(Status status){

    //     return ticketRepository.findByStatus(status, null).map(this::mapToResponseDTO);
    // }

    // public Page<TicketResponseDTO> findByPriority(Priority priority){

    //     return ticketRepository.findByPriority(priority, null).map(this::mapToResponseDTO);
    // }

    public TicketResponseDTO updateTicketStatus(Long id,Status status){

        Optional<Ticket> existingTicket = ticketRepository.findById(id);
        if(existingTicket.isPresent()){
            Ticket dbTicket = existingTicket.get();
            dbTicket.setStatus(status);
            Ticket updatedTicket = ticketRepository.save(dbTicket);
            return mapToResponseDTO(updatedTicket);
        }else{
            throw new TicketNotFoundException("Ticket not found with id: "+ id);
        }

    }

    public void deleteTicket(Long id){
        Optional<Ticket> existingTicket = ticketRepository.findById(id);
        if(existingTicket.isPresent()){
            Ticket dbTicket = existingTicket.get();
            ticketRepository.delete(dbTicket);
        }else{
            throw new TicketNotFoundException("Ticket not found with id: "+ id);
        }
    }

}

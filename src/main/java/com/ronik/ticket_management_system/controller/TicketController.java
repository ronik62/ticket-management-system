package com.ronik.ticket_management_system.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronik.ticket_management_system.dto.TicketRequestDTO;
import com.ronik.ticket_management_system.dto.TicketResponseDTO;
import com.ronik.ticket_management_system.dto.TicketStatusUpdateDTO;
import com.ronik.ticket_management_system.entity.Ticket;
import com.ronik.ticket_management_system.enums.Priority;
import com.ronik.ticket_management_system.enums.Status;
import com.ronik.ticket_management_system.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
    name = "Ticket Management",
    description = "APIs for managing support tickets"

)

@RestController
@RequestMapping("/api")
public class TicketController {
    
    private final TicketService ticketService;
    
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // create a method to create a ticket
    @Operation(
        summary = "create a new ticket",
        description = "creates a new support tickets and returns the created ticket details."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ticket created successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request data"
        )
    })
    @PostMapping("/tickets")
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketRequestDTO requestDTO){
        TicketResponseDTO responseDTO = ticketService.createTicket(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // create a method to get all tickets
    @Operation(
        summary = "Get all tickets",
        description = "Returns paginated tickets with optional filtering,sorting and pagination."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Tickets retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters"
        )
    })
    @GetMapping("/tickets")
    public ResponseEntity<Page<TicketResponseDTO>> getAllTickets(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size, @RequestParam(required = false) Priority priority, @RequestParam(required = false) Status status,@RequestParam(required = false) String requesterName,@RequestParam(defaultValue = "createdAt") String sortBy,@RequestParam(defaultValue = "desc") String direction){
        Page<TicketResponseDTO> alltickets = ticketService.getAllTickets(page,size,priority,status,requesterName,sortBy,direction);
        return ResponseEntity.ok(alltickets);
    }

    // create a method to find a ticket by id
    @Operation(
        summary = "Get ticket by ID",
        description = "Returns the ticket details for the specified ticket ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ticket retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ticket not found"
        )
    })
    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id){
        
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    // create a method to update a ticket by id
    @Operation(
        summary = "Update ticket",
        description = "Updates the details of an existing ticket."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ticket updated successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request data"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ticket not found"
        )
    })
    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @Valid @RequestBody Ticket ticket){
        Ticket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    // merged all below endpoints into single one
    // @GetMapping("/tickets/requester/{requesterName}")
    // public ResponseEntity<Page<TicketResponseDTO>> findTicketByRequesterName(@PathVariable String requesterName){

    //     Page<TicketResponseDTO> tickets = ticketService.findByRequesterName(requesterName);

    //     return ResponseEntity.ok(tickets);
    // }

    // @GetMapping("/tickets/status/{status}")
    // public ResponseEntity<Page<TicketResponseDTO>> findTicketByStatus(@PathVariable Status status){
        
    //     Page<TicketResponseDTO> tickets = ticketService.findByStatus(status);

    //     return ResponseEntity.ok(tickets);
    // }

    // @GetMapping("/tickets/priority/{priority}")
    // public ResponseEntity<Page<TicketResponseDTO>> findTicketByPriority(@PathVariable Priority priority){
        
    //     Page<TicketResponseDTO> tickets = ticketService.findByPriority(priority);

    //     return ResponseEntity.ok(tickets);
    // }

    @Operation(
        summary = "Update ticket status",
        description = "Updates only the status of the specified ticket."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ticket status updated successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid status value"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ticket not found"
        )
    })
    @PutMapping("/tickets/{id}/status")
    public ResponseEntity<TicketResponseDTO> updateTicketStatus(@PathVariable Long id,@RequestBody TicketStatusUpdateDTO ticketStatusUpdateDTO){
        TicketResponseDTO responseDTO = ticketService.updateTicketStatus(id,ticketStatusUpdateDTO.getStatus());

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
        summary = "Delete ticket",
        description = "Deletes the ticket with the specified ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ticket deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ticket not found"
        )
    })
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);
        
        return ResponseEntity.ok("Ticket deleted successfully");
    }
}

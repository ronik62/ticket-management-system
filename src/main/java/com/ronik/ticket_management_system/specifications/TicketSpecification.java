package com.ronik.ticket_management_system.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.ronik.ticket_management_system.entity.Ticket;
import com.ronik.ticket_management_system.enums.Priority;
import com.ronik.ticket_management_system.enums.Status;

public class TicketSpecification {
    
    public static Specification<Ticket> hasStatus(Status status){
        
        return (root,query,criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("status"),status);
        
    }

    public static Specification<Ticket> hasPriority(Priority priority){
        
        return (root,query,criteriaBuilder) ->
            criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Ticket> hasRequesterName(String requesterName){

        return (root,query,criteriaBuilder) ->
            criteriaBuilder.equal(root.get("requesterName"),requesterName);
    }
}

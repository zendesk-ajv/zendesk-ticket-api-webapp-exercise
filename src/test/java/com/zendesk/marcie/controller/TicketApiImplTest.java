package com.zendesk.marcie.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.zendesk.marcie.dto.DataContent;
import com.zendesk.marcie.dto.Ticket;
import com.zendesk.marcie.service.TicketService;

import jakarta.xml.bind.ValidationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class TicketApiImplTest {

    @Mock
    TicketService ticketService;

    @InjectMocks
    TicketApiImpl ticketApiImpl;

    @Test
    void getAllTicket() {
        DataContent dataContent = new DataContent(); //set the required fields
        Mockito.when(ticketService.getAllTicket()).thenReturn(Flux.just(dataContent));
        
        StepVerifier.create(ticketApiImpl.getAllTicket())
            .expectNext(dataContent)
            .verifyComplete();;
    }

    @Test
    void getAllTicket_pagination() {
        int pageNumber = 1;
        int pageSize = 5;
        List<Ticket> ticketPage = Collections.emptyList(); // create a page object
        
        Mockito.when(ticketService.findPaginatedTicket(pageNumber, pageSize)).thenReturn(ticketPage);
        
        assertEquals(ticketPage, ticketApiImpl.getAllTicket(pageNumber, pageSize));
    }

    @Test
    void getTicket() throws ValidationException {
        int ticketId = 123;
        DataContent dataContent = new DataContent(); //set the required fields
        Ticket ticket = new Ticket();
        Mockito.when(ticketService.getTicket(ticketId)).thenReturn(Mono.just(ticket));

        StepVerifier.create(ticketApiImpl.getTicket(ticketId))
            .expectNext(ticket)
            .verifyComplete();
    }

    @Test
    void updateTicket() throws ValidationException {
        int ticketId = 123;
        Ticket ticket = new Ticket(); //set the required fields
        DataContent dataContent = new DataContent();
        Mockito.when(ticketService.updateTicket(ticketId, dataContent)).thenReturn(Mono.just(ticket));
        
        StepVerifier.create(ticketApiImpl.updateTicket(ticketId, dataContent))
            .expectNext(ticket)
            .verifyComplete();
    }

    @Test
    void deleteTicket() throws ValidationException {
        int ticketId = 123;
        Mockito.when(ticketService.deleteTicket(ticketId)).thenReturn(Mono.empty());
        
        StepVerifier.create(ticketApiImpl.deleteTicket(ticketId))
            .expectComplete()
            .verify();
    }
}

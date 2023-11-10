package com.zendesk.marcie.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        Page<Ticket> ticketPage = new PageImpl<>(Collections.emptyList()); // create a page object
        
        Mockito.when(ticketService.findPaginatedTicket(pageNumber, pageSize)).thenReturn(ticketPage);
        
        assertEquals(ticketPage, ticketApiImpl.getAllTicket(pageNumber, pageSize));
    }

    @Test
    void getTicket() throws ValidationException {
        int ticketId = 123;
        DataContent dataContent = new DataContent(); //set the required fields
        Mockito.when(ticketService.getTicket(ticketId)).thenReturn(Mono.just(dataContent));

        StepVerifier.create(ticketApiImpl.getTicket(ticketId))
            .expectNext(dataContent)
            .verifyComplete();
    }

    @Test
    void updateTicket() throws ValidationException {
        int ticketId = 123;
        DataContent dataContent = new DataContent(); //set the required fields
        Mockito.when(ticketService.updateTicket(ticketId, dataContent)).thenReturn(Mono.just(dataContent));
        
        StepVerifier.create(ticketApiImpl.updateTicket(ticketId, dataContent))
            .expectNext(dataContent)
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

package com.zendesk.marcie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zendesk.marcie.dto.DataContent;
import com.zendesk.marcie.dto.Ticket;
import com.zendesk.marcie.integration.ZendeskSupportApiService;
import com.zendesk.marcie.utility.TicketValidator;

import jakarta.xml.bind.ValidationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TicketService {

    @Autowired
    private ZendeskSupportApiService zendeskSupportApiService;

    @Autowired
    private TicketValidator ticketValidator;

    public Flux<DataContent> getAllTicket() {
        return zendeskSupportApiService.getAllTicket();
    }

    public Mono<Ticket> getTicket(Integer ticketId) throws ValidationException {
        ticketValidator.validateUserInput(ticketId);
        return zendeskSupportApiService.getTicket(ticketId);
    }

    public Mono<Ticket> updateTicket(Integer ticketId, DataContent dataContent) throws ValidationException {
        ticketValidator.validateUserInput(ticketId);
        ticketValidator.validateDataContent(dataContent);
        return zendeskSupportApiService.updateTicket(ticketId, dataContent);
    }

    public Mono<Object> deleteTicket(Integer ticketId) throws ValidationException {
        ticketValidator.validateUserInput(ticketId);
        return zendeskSupportApiService.deleteTicket(ticketId);
    }

    public Page<Ticket> findPaginatedTicket(int pageNumber, int pageSize) {
        // List<DataContent> dataList = getAllTicket().collectList().block();

        //validation pageNumber zero-based page number, must not be negative.

        //pageSize the size of the page to be returned, must be greater than 0.
        
        List<Ticket> listOfTickets = new ArrayList<>(getAllTicket().collectList().block().get(0).getTickets());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // getAllTicket().collectList().subscribe(dataList::addAll);
        // getAllTicket().doOnNext(dataList::add).subscribe();

        // getAllTicket().subscribe(dataList::add);
        // List<Ticket> listOfTickets= dataList.get(0).getTickets();

        // Calculate start and end item indexes
        System.out.println("size of ticket list " + listOfTickets.size());
        int startItem = (int) pageable.getOffset();
        int endItem = Math.min(startItem + pageSize, listOfTickets.size());

        // Get sublist and create PageImpl
        List<Ticket> pageList = listOfTickets.subList(startItem, endItem);
        Page<Ticket> dataContentPage = new PageImpl<>(pageList, pageable, listOfTickets.size());
        return dataContentPage;
    }
}

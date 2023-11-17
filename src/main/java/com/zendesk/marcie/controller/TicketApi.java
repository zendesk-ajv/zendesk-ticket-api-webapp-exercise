package com.zendesk.marcie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.ValidationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import com.zendesk.marcie.dto.DataContent;
import com.zendesk.marcie.dto.Ticket;

@Tag(name = "Zendesk Ticket Viewer", description = "the Ticket Api")
public interface TicketApi {

        @Operation(summary = "Fetch all tickets", description = "fetches all Tickets entities and their data from zendesk api")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        public Flux<DataContent> getAllTicket();

        @Operation(summary = "Fetch all tickets by page and size", description = "fetches all Tickets entities and their data from zendesk api with page number and size")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        public List<Ticket> getAllTicket(Integer pageNumber, Integer pageSize);

        @Operation(summary = "get a single ticket", description = "Get a single ticket information")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "successfully get a ticket")
        })
        public Mono<Ticket> getTicket(Integer ticketId) throws ValidationException;

        @Operation(summary = "Update ticket", description = "Update a single ticket information")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "successfully get a ticket")
        })
        public Mono<Ticket> updateTicket(Integer ticketId, DataContent dataContent) throws ValidationException;

        // /api/v2/tickets/{ticket_id}
        @Operation(summary = "Delete single ticket", description = "Delete single ticket")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "successfully get a ticket")
        })
        public Mono<Object> deleteTicket(Integer ticketId) throws ValidationException;

}
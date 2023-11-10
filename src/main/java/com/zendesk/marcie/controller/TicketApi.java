package com.zendesk.marcie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.ValidationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.zendesk.marcie.dto.DataContent;

@Tag(name = "Zendesk Ticket Viewer", description = "the Ticket Api")
public interface TicketApi {

    @Operation(
            summary = "Fetch all tickets",
            description = "fetches all Tickets entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public Flux<DataContent> getAllTicket();

    @Operation(
            summary = "get a single ticket",
            description = "Get a single ticket information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully get a ticket")
    })
    public Mono<DataContent> getTicket(@PathVariable(value = "ticketId") Integer ticketId) throws ValidationException;

    @Operation(
            summary = "Update ticket",
            description = "Get a single ticket information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully get a ticket")
    })
    public Mono<DataContent> updateTicket(@PathVariable(value = "ticketId") Integer ticketId, @RequestBody DataContent dataContent) throws ValidationException;


    // /api/v2/tickets/{ticket_id}
    @Operation(
            summary = "Delete single ticket",
            description = "Delete single ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully get a ticket")
    })
    public Mono<Object> deleteTicket(@PathVariable(value = "ticketId") Integer ticketId) throws ValidationException;

}
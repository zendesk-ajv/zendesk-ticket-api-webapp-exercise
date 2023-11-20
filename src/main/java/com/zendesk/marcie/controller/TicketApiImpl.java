package com.zendesk.marcie.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zendesk.marcie.dto.DataContent;
import com.zendesk.marcie.dto.Ticket;
import com.zendesk.marcie.service.TicketService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.xml.bind.ValidationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@SecurityRequirement(name = "zendeskapi")
public class TicketApiImpl implements TicketApi {

    @Autowired
    private TicketService ticketService;

    // GET request

    @GetMapping("/get-all-tickets")
    public Flux<DataContent> getAllTicket() {
        return ticketService.getAllTicket();
    }

    @GetMapping("/get-all-tickets-page")
    public List<Ticket> getAllTicket(@RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestParam(value = "pageSize") Integer pageSize) {
        if (pageNumber != null || pageSize != null) {
            return ticketService.findPaginatedTicket(pageNumber, pageSize);
        } 
        return Collections.emptyList();
    }

    @GetMapping("/get-ticket/{ticketId}")
    public Mono<Ticket> getTicket(@PathVariable(value = "ticketId") Integer ticketId) throws ValidationException {
        return ticketService.getTicket(ticketId);

    }

    // PUT request
    @PutMapping("/update-ticket/{ticketId}")
    public Mono<Ticket> updateTicket(@PathVariable(value = "ticketId") Integer ticketId,
            @RequestBody DataContent dataContent) throws ValidationException {

        return ticketService.updateTicket(ticketId, dataContent);

    }

    // DELETE request
    @DeleteMapping("/delete-ticket/{ticketId}")
    public Mono<Object> deleteTicket(@PathVariable(value = "ticketId") Integer ticketId) throws ValidationException {
        return ticketService.deleteTicket(ticketId);// null;webClient.delete().uri(url).retrieve().bodyToMono(Void.class);
    }

    /*
     * POST request
     * 
     * @PostMapping("/test")
     * public Mono<String> postExample(String url, Object request) {
     * return
     * null;//webClient.post().uri(url).bodyValue(request).retrieve().bodyToMono(
     * String.class);
     * }
     */

    /*
     * @GetMapping("/myentities")
     * public Page<MyEntity> getMyEntities(@RequestParam int page, @RequestParam int
     * size) {
     * List<MyEntity> entities = //... some method that fetches your entities
     * return myEntityService.findPaginated(entities, page, size);
     * }
     */
}

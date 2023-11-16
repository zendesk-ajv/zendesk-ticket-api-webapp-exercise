package com.zendesk.marcie.integration;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.test.StepVerifier;

import java.io.IOException;

@SpringBootTest
public class ZendeskSupportApiServiceTest {

    private MockWebServer mockBackEnd;
    private WebTestClient webTestClient;

    @InjectMocks
    private ZendeskSupportApiService zendeskSupportApiService;

    @Value("${zendesk.account.url}")
    private String url;

    @Value("${zendesk.account.username}")
    private String username;

    @Value("${zendesk.account.password}")
    private String password;

    private MockWebServer mockWebServer;
    


    /*
    private MockWebServer mockWebServer;
    
    private WebClient webClient;

    @InjectMocks
    private ZendeskSupportApiService yourServiceClass; // replace with your actual class name

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        }

    @Test
    void getTicket_returnsDataContent() {
        String expectedResponse = "{\"insert_your_JSON_here\"}";
        mockWebServer.enqueue(new MockResponse()
                //.setBody(expectedResponse)
                .addHeader("Content-Type", "application/json")
                .setResponseCode(HttpStatus.OK.value()));

        Mono<DataContent> result = yourServiceClass.getTicket(1);

        StepVerifier.create(result)
                .expectNextMatches(dataContent -> ( dataContent.getTickets().size()!=0 ))
                .verifyComplete();
    }
    
    
    



    @Test
    public void testGetTicket() {
        Integer ticketId = 1;

        // set up a planned response
        mockBackEnd.enqueue(new MockResponse()
            //.setBody("{\"\"}")
            .addHeader("Content-Type", "application/json"));

         zendeskSupportApiService.getTicket(1);   
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/v2/tickets/" + ticketId).build())
            .exchange()
            .expectStatus().isOk()
            .expectBody(DataContent.class); // Your expected return type
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }*/

    
    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String testUrl = String.format("http://localhost:%s",
                mockWebServer.getPort());

        zendeskSupportApiService = new ZendeskSupportApiService(WebClient.builder().baseUrl(testUrl).build());
    }

    @Test
    void getTicketTest() {
        int ticketId = 1;
        String ticketJson = "{\"field\": \"value\"}"; // replace this with actual ticket data in JSON format

        mockWebServer.enqueue(
                new MockResponse()
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        //.setBody(ticketJson)
                        .setResponseCode(200)
        );

        StepVerifier.create(zendeskSupportApiService.getTicket(ticketId)).expectNextCount(1)
                //.assertNext(null)
                .verifyComplete();
    }


    @Test
    void getAllTicketTest() {
        int ticketId = 1;
        String ticketJson = "{\"field\": \"value\"}"; // replace this with actual ticket data in JSON format

        mockWebServer.enqueue(
                new MockResponse()
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        //.setBody(ticketJson)
                        .setResponseCode(200)
        );

        StepVerifier.create(zendeskSupportApiService.getAllTicket())//.expectNextCount(1)
                //.assertNext(null)
                .verifyComplete();
    }

    
    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

}
package com.zendesk.marcie.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl("https://z3nzendeskcodingchallengesupport.zendesk.com")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.setBasicAuth("ajay.vaishnav@zendesk.com", "12345");
                })
                .build();
    }

}

package com.example.tgbotecho.client;

import com.example.tgbotecho.domain.MessageInner;
import com.example.tgbotecho.domain.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class EchoBotHttpClient {

    private final RestTemplate restTemplate;
    private final String url;

    public EchoBotHttpClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public Response receiveEchoMessage(MessageInner messageInner) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .pathSegment("receiveEchoMessage")
                .build()
                .toUri();

        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(messageInner, null), Response.class).getBody();
    }

}

package org.iliuta.footballhub.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FootballApiClient {

    private final WebClient footballClient;


    public FootballApiClient(WebClient footballClient) {
        this.footballClient = footballClient;
    }
}

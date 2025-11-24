package org.iliuta.footballhub.client;

import org.iliuta.footballhub.client.dto.league.ExternalLeagueResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FootballApiClient {

    private final WebClient footballClient;


    public FootballApiClient(WebClient footballClient) {
        this.footballClient = footballClient;
    }

    public ExternalLeagueResponseDTO getLeaguesByCountry(String countryCode) {
        return footballClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/leagues")
                        .queryParam("code", countryCode)
                        .build())
                .retrieve()
                .bodyToMono(ExternalLeagueResponseDTO.class)
                .block();
    }
}

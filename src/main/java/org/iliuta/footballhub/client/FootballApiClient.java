package org.iliuta.footballhub.client;

import org.iliuta.footballhub.client.dto.league.ExternalLeagueResponseDTO;
import org.iliuta.footballhub.client.dto.statistics.ExternalTeamStatisticsDTO;
import org.iliuta.footballhub.client.dto.statistics.ExternalTeamStatisticsResponseDTO;
import org.iliuta.footballhub.client.dto.team.ExternalTeamResponseDTO;
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

    public ExternalTeamResponseDTO getTeamsByLeagueIdAndSeasonYear(Integer leagueId, Integer seasonYear) {
        return footballClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("league", leagueId)
                        .queryParam("season", seasonYear)
                        .build())
                .retrieve()
                .bodyToMono(ExternalTeamResponseDTO.class)
                .block();
    }

    public ExternalTeamStatisticsResponseDTO getStatisticsByLeagueIdAndTeamIdAndSeasonYear(
            Integer leagueId, Integer teamId, Integer seasonYear
    ) {
        return footballClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams/statistics")
                        .queryParam("league", leagueId)
                        .queryParam("team", teamId)
                        .queryParam("season", seasonYear)
                        .build())
                .retrieve()
                .bodyToMono(ExternalTeamStatisticsResponseDTO.class)
                .block();

    }
}

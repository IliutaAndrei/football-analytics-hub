package org.iliuta.footballhub.standings.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.standings.ExternalStandingsDTO;
import org.iliuta.footballhub.client.dto.standings.ExternalStandingsSummaryDTO;
import org.iliuta.footballhub.standings.dto.StandingDTO;
import org.iliuta.footballhub.standings.dto.StandingsResponseDTO;
import org.iliuta.footballhub.standings.mapper.StandingsMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StandingsService {

    private final StandingsMapper standingsMapper;
    private final FootballApiClient footballApiClient;


    public StandingsService(StandingsMapper standingsMapper, FootballApiClient footballApiClient) {
        this.standingsMapper = standingsMapper;
        this.footballApiClient = footballApiClient;
    }

    public StandingsResponseDTO getStandingsByLeagueIdAndSeasonYear(
            Integer leagueId, Integer seasonYear) {

        List<StandingDTO> standingList = new ArrayList<>();
        var response =
                footballApiClient.getStandingsByLeagueIdAndSeasonYear(leagueId, seasonYear);

        if (response == null || response.response() == null) {
            throw new RuntimeException("Standings not available");
        }

        var league = response.response().getFirst().league();
        var standings = league.standings();

        for (List<ExternalStandingsSummaryDTO> standing : standings) {
            for (ExternalStandingsSummaryDTO externalStandingsSummaryDTO : standing) {
                standingList.add(standingsMapper.toStandingDTO(externalStandingsSummaryDTO));
            }
        }

        return new StandingsResponseDTO(
                league.id(),
                league.name(),
                league.country(),
                league.logo(),
                league.flag(),
                league.season(),
                standingList);
    }
}

package org.iliuta.footballhub.teams.seasons.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.teams.seasons.dto.TeamSeasonsDTO;
import org.iliuta.footballhub.teams.seasons.mapper.TeamSeasonsMapper;
import org.springframework.stereotype.Service;

@Service
public class TeamSeasonsService {

    private final FootballApiClient footballApiClient;
    private final TeamSeasonsMapper teamSeasonsMapper;


    public TeamSeasonsService(FootballApiClient footballApiClient,
                              TeamSeasonsMapper teamSeasonsMapper) {
        this.footballApiClient = footballApiClient;
        this.teamSeasonsMapper = teamSeasonsMapper;
    }

    public TeamSeasonsDTO getSeasonsByTeamId(Integer teamId) {
        var response = footballApiClient
                .getSeasonsByTeamId(teamId);

        if (response == null || response.response() == null) {
            throw new RuntimeException("Seasons not available");
        }

        return teamSeasonsMapper.toTeamSeasonsDTO(response);
    }
}

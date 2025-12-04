package org.iliuta.footballhub.team.statistics.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.team.statistics.dto.TeamStatisticsDTO;
import org.iliuta.footballhub.team.statistics.mapper.TeamStatisticsMapper;
import org.springframework.stereotype.Service;

@Service
public class TeamStatisticsService {

    private final FootballApiClient footballApiClient;
    private final TeamStatisticsMapper teamStatisticsMapper;

    public TeamStatisticsService(FootballApiClient footballApiClient, TeamStatisticsMapper teamStatisticsMapper) {
        this.footballApiClient = footballApiClient;
        this.teamStatisticsMapper = teamStatisticsMapper;
    }

    public TeamStatisticsDTO getTeamStatistics(Integer leagueId, Integer teamId, Integer seasonYear) {
        var response = footballApiClient
                        .getStatisticsByLeagueIdAndTeamIdAndSeasonYear(leagueId, teamId, seasonYear);

        if (response == null || response.response() == null) {
            throw new RuntimeException("Statistics not available");
        }
        return teamStatisticsMapper.toTeamStatisticsDTO(response.response());
    }
}

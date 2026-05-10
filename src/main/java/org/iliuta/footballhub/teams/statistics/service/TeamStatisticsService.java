package org.iliuta.footballhub.teams.statistics.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.teams.TeamEntity;
import org.iliuta.footballhub.teams.TeamRepository;
import org.iliuta.footballhub.teams.statistics.dto.TeamStatisticsDTO;
import org.iliuta.footballhub.teams.statistics.mapper.TeamStatisticsMapper;
import org.springframework.stereotype.Service;

@Service
public class TeamStatisticsService {

    private final FootballApiClient footballApiClient;
    private final TeamRepository teamRepository;
    private final TeamStatisticsMapper teamStatisticsMapper;

    public TeamStatisticsService(FootballApiClient footballApiClient, TeamRepository teamRepository, TeamStatisticsMapper teamStatisticsMapper) {
        this.footballApiClient = footballApiClient;
        this.teamRepository = teamRepository;
        this.teamStatisticsMapper = teamStatisticsMapper;
    }

    public TeamStatisticsDTO getTeamStatistics(Integer leagueId, Integer teamId, Integer seasonYear) {

        TeamEntity entity = teamRepository.
                findByIdAndLeague_IdAndSeason_Year(teamId, leagueId, seasonYear)
                .orElseThrow(() -> new RuntimeException("Team not found"));


        var response = footballApiClient
                .getStatisticsByLeagueIdAndTeamIdAndSeasonYear(
                        entity.getLeague().getExternalId(), entity.getExternalId(), entity.getSeason().getYear()
                );

        if (response == null || response.response() == null) {
            throw new RuntimeException("Statistics not available");
        }
        return teamStatisticsMapper.toTeamStatisticsDTO(response.response());
    }
}

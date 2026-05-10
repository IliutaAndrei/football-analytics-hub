package org.iliuta.footballhub.fixtures.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.fixtures.dto.FixtureResponseDTO;
import org.iliuta.footballhub.fixtures.mapper.FixturesMapper;
import org.iliuta.footballhub.leagues.LeagueRepository;
import org.iliuta.footballhub.teams.TeamRepository;
import org.iliuta.footballhub.teams.service.TeamService;
import org.springframework.stereotype.Service;

@Service
public class FixtureService {

    private final FootballApiClient footballApiClient;
    private final FixturesMapper mapper;
    private final TeamService teamService;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;


    public FixtureService(FootballApiClient footballApiClient, FixturesMapper mapper, TeamService teamService, LeagueRepository leagueRepository, TeamRepository teamRepository) {
        this.footballApiClient = footballApiClient;
        this.mapper = mapper;
        this.teamService = teamService;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    public FixtureResponseDTO getFixturesByLeagueIdAndSeasonYear(
            Integer leagueId, Integer seasonYear
    ) {
        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found: " + leagueId));

        var response = footballApiClient
                .getFixturesByLeagueIdAndSeasonYear(league.getExternalId(), seasonYear);

        return mapper.toFixtureResponseDTO(response);
    }

    public FixtureResponseDTO getFixturesByLeagueIdSeasonYearAndTeamId(
            Integer leagueId, Integer seasonYear, Integer teamId
    ) {
        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found id: " + leagueId));
        var team = teamRepository
                .findByIdAndLeague_IdAndSeason_Year(teamId, leagueId, seasonYear)
                .orElseThrow(() ->
                        new RuntimeException("Team not found with id: " + teamId)
                );

        teamService.syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);

        var response = footballApiClient
                .getFixturesByLeagueIdSeasonYearAndTeamId(
                        league.getExternalId(), seasonYear, team.getExternalId()
                );

        return mapper.toFixtureResponseDTO(response);
    }


}

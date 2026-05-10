package org.iliuta.footballhub.standings.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.standings.ExternalStandingsSummaryDTO;
import org.iliuta.footballhub.leagues.LeagueEntity;
import org.iliuta.footballhub.leagues.LeagueRepository;
import org.iliuta.footballhub.standings.dto.StandingDTO;
import org.iliuta.footballhub.standings.dto.StandingsResponseDTO;
import org.iliuta.footballhub.standings.dto.TeamDTO;
import org.iliuta.footballhub.standings.mapper.StandingsMapper;
import org.iliuta.footballhub.teams.TeamRepository;
import org.iliuta.footballhub.teams.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StandingsService {

    private final StandingsMapper standingsMapper;
    private final FootballApiClient footballApiClient;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;


    public StandingsService(StandingsMapper standingsMapper,
                            FootballApiClient footballApiClient,
                            LeagueRepository leagueRepository, TeamRepository teamRepository,
                            TeamService teamService) {
        this.standingsMapper = standingsMapper;
        this.footballApiClient = footballApiClient;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.teamService = teamService;
    }

    public StandingsResponseDTO getStandingsByLeagueIdAndSeasonYear(
            Integer leagueId, Integer seasonYear) {

        List<StandingDTO> standingList = new ArrayList<>();

        LeagueEntity league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found: " + leagueId));

        var response =
                footballApiClient.getStandingsByLeagueIdAndSeasonYear(league.getExternalId(), seasonYear);

        if (response == null || response.response() == null || response.response().isEmpty()) {
            throw new RuntimeException("Standings not available");
        }

        teamService.syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);

        var leagueData = response.response().getFirst().league();
        var standings = leagueData.standings();

        for (List<ExternalStandingsSummaryDTO> standing : standings) {
            for (ExternalStandingsSummaryDTO externalStandingsSummaryDTO : standing) {
                standingList.add(standingsMapper.toStandingDTO(externalStandingsSummaryDTO));
            }
        }
        for (int i = 0; i < standingList.size(); i++) {
            StandingDTO standingDTO = standingList.get(i);
            var team = teamRepository.findByExternalIdAndLeague_IdAndSeason_Year(
                            standingDTO.team().externalId(),
                            leagueId,
                            seasonYear)
                    .orElseThrow(() -> new RuntimeException("Team is no available"));
            var newTeam = new TeamDTO(
                    team.getId(),
                    team.getExternalId(),
                    team.getName(),
                    team.getLogo()
            );
            StandingDTO newStandingDTO = new StandingDTO(
                    standingDTO.rank(),
                    newTeam,
                    standingDTO.points(),
                    standingDTO.goalsDiff(),
                    standingDTO.form(),
                    standingDTO.summary()
            );
            standingList.set(i, newStandingDTO);
        }

        return new StandingsResponseDTO(
                leagueData.id(),
                leagueData.name(),
                leagueData.country(),
                leagueData.logo(),
                leagueData.flag(),
                leagueData.season(),
                standingList
        );
    }
}

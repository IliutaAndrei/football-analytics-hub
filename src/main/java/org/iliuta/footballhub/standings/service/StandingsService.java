package org.iliuta.footballhub.standings.service;

import lombok.extern.slf4j.Slf4j;
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
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class StandingsService {

    private final StandingsMapper standingsMapper;
    private final FootballApiClient footballApiClient;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;

    public StandingsService(StandingsMapper standingsMapper,
                            FootballApiClient footballApiClient,
                            LeagueRepository leagueRepository,
                            TeamRepository teamRepository,
                            TeamService teamService) {
        this.standingsMapper = standingsMapper;
        this.footballApiClient = footballApiClient;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.teamService = teamService;
    }

    public StandingsResponseDTO getStandingsByLeagueIdAndSeasonYear(
            Integer leagueId, Integer seasonYear) {

        LeagueEntity league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + leagueId));

        log.info("Fetching standings for league {} ({}) season {}",
                league.getName(), leagueId, seasonYear);

        var response = footballApiClient.getStandingsByLeagueIdAndSeasonYear(
                league.getExternalId(), seasonYear);

        if (response == null || response.response() == null || response.response().isEmpty()) {
            log.info("No standings available for league {} season {} (likely a knockout competition)",
                    league.getName(), seasonYear);

            return new StandingsResponseDTO(
                    league.getExternalId(),
                    league.getName(),
                    league.getCountry().getName(),
                    league.getLogo(),
                    league.getCountry().getFlag(),
                    seasonYear,
                    Collections.emptyList()
            );
        }

        teamService.syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);

        var leagueData = response.response().getFirst().league();
        var standings = leagueData.standings();

        List<StandingDTO> standingList = new ArrayList<>();

        for (List<ExternalStandingsSummaryDTO> standing : standings) {
            for (ExternalStandingsSummaryDTO externalStanding : standing) {
                standingList.add(standingsMapper.toStandingDTO(externalStanding));
            }
        }

        for (int i = 0; i < standingList.size(); i++) {
            StandingDTO standingDTO = standingList.get(i);

            var team = teamRepository.findByExternalIdAndLeague_IdAndSeason_Year(
                            standingDTO.team().externalId(),
                            leagueId,
                            seasonYear)
                    .orElseThrow(() -> new RuntimeException(
                            "Team with external id " + standingDTO.team().externalId() +
                            " not found in league " + leagueId + " season " + seasonYear));

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

        log.info("Successfully fetched {} standings for league {}",
                standingList.size(), league.getName());

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
package org.iliuta.footballhub.fixtures.service;

import lombok.extern.slf4j.Slf4j;
import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.fixtures.dto.FixtureResponseDTO;
import org.iliuta.footballhub.fixtures.mapper.FixturesMapper;
import org.iliuta.footballhub.leagues.LeagueRepository;
import org.iliuta.footballhub.teams.TeamRepository;
import org.iliuta.footballhub.teams.service.TeamService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FixtureService {

    private final FootballApiClient footballApiClient;
    private final FixturesMapper mapper;
    private final TeamService teamService;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    public FixtureService(FootballApiClient footballApiClient,
                          FixturesMapper mapper,
                          TeamService teamService,
                          LeagueRepository leagueRepository,
                          TeamRepository teamRepository) {
        this.footballApiClient = footballApiClient;
        this.mapper = mapper;
        this.teamService = teamService;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Returnează toate meciurile pentru o ligă și sezon.
     * Apelează direct API-ul extern - nu salvăm fixtures în DB.
     */
    public FixtureResponseDTO getFixturesByLeagueIdAndSeasonYear(
            Integer leagueId, Integer seasonYear) {

        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + leagueId));

        log.info("Fetching fixtures for league {} ({}) season {}",
                league.getName(), leagueId, seasonYear);

        var response = footballApiClient
                .getFixturesByLeagueIdAndSeasonYear(league.getExternalId(), seasonYear);

        return mapper.toFixtureResponseDTO(response);
    }

    /**
     * Returnează meciurile pentru o echipă specifică într-o ligă și sezon.
     * IMPORTANT: teamId este id-ul INTERN din baza noastră de date.
     */
    public FixtureResponseDTO getFixturesByLeagueIdSeasonYearAndTeamId(
            Integer leagueId, Integer seasonYear, Integer teamId) {

        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + leagueId));

        // Caută echipa după id-ul INTERN
        var team = teamRepository
                .findByIdAndLeague_IdAndSeason_Year(teamId, leagueId, seasonYear);

        // Dacă nu există, sincronizează și încearcă din nou
        if (team.isEmpty()) {
            log.info("Team {} not found locally. Syncing teams for league {} season {}",
                    teamId, leagueId, seasonYear);
            teamService.syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);
            team = teamRepository.findByIdAndLeague_IdAndSeason_Year(teamId, leagueId, seasonYear);
        }

        var finalTeam = team.orElseThrow(() ->
                new RuntimeException("Team not found with id: " + teamId +
                                     " in league " + leagueId + " season " + seasonYear));

        log.info("Fetching fixtures for team {} ({}) in league {} season {}",
                finalTeam.getName(), teamId, league.getName(), seasonYear);

        var response = footballApiClient
                .getFixturesByLeagueIdSeasonYearAndTeamId(
                        league.getExternalId(),
                        seasonYear,
                        finalTeam.getExternalId()
                );

        return mapper.toFixtureResponseDTO(response);
    }
}
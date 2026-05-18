package org.iliuta.footballhub.teams.service;

import lombok.extern.slf4j.Slf4j;
import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.teams.ExternalTeamDTO;
import org.iliuta.footballhub.client.dto.teams.ExternalTeamInfoDTO;
import org.iliuta.footballhub.client.dto.teams.ExternalVenueDTO;
import org.iliuta.footballhub.leagues.LeagueEntity;
import org.iliuta.footballhub.leagues.LeagueRepository;
import org.iliuta.footballhub.leagues.SeasonEntity;
import org.iliuta.footballhub.leagues.SeasonRepository;
import org.iliuta.footballhub.teams.TeamEntity;
import org.iliuta.footballhub.teams.TeamRepository;
import org.iliuta.footballhub.teams.VenueEntity;
import org.iliuta.footballhub.teams.VenueRepository;
import org.iliuta.footballhub.teams.dto.TeamDTO;
import org.iliuta.footballhub.teams.mapper.ExternalTeamMapper;
import org.iliuta.footballhub.teams.mapper.InternalTeamMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class TeamService {

    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final VenueRepository venueRepository;
    private final ExternalTeamMapper teamMapper;
    private final FootballApiClient footballApiClient;
    private final InternalTeamMapper internalTeamMapper;

    public TeamService(LeagueRepository leagueRepository,
                       SeasonRepository seasonRepository,
                       TeamRepository teamRepository,
                       VenueRepository venueRepository,
                       ExternalTeamMapper teamMapper,
                       FootballApiClient footballApiClient,
                       InternalTeamMapper internalTeamMapper) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.teamRepository = teamRepository;
        this.venueRepository = venueRepository;
        this.teamMapper = teamMapper;
        this.footballApiClient = footballApiClient;
        this.internalTeamMapper = internalTeamMapper;
    }

    // PUBLIC API METHODS

    /**
     * Găsește o echipă după externalId în contextul unei ligile și sezon.
     * Returnează direct TeamDTO, nu doar id-ul intern.
     */
    public TeamDTO getTeamByExternalIdInContext(Integer externalTeamId, Integer leagueId, Integer seasonYear) {
        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + leagueId));

        var team = teamRepository.findByExternalIdAndLeague_IdAndSeason_Year(
                externalTeamId, leagueId, seasonYear);

        if (team.isEmpty()) {
            log.info("Team with external id {} not found locally. Syncing teams...", externalTeamId);
            syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);
            team = teamRepository.findByExternalIdAndLeague_IdAndSeason_Year(
                    externalTeamId, leagueId, seasonYear);
        }

        return team.map(internalTeamMapper::toTeamDTO)
                .orElseThrow(() -> new RuntimeException(
                        "Team with external id " + externalTeamId +
                        " not found in league " + leagueId + " season " + seasonYear));
    }

    /**
     * Caută o echipă după id-ul INTERN din contextul unei ligile și sezon.
     * Dacă nu găsește, sincronizează de la API și încearcă din nou.
     */
    public TeamDTO getTeamByIdInContext(Integer teamId, Integer leagueId, Integer seasonYear) {
        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + leagueId));

        var team = teamRepository.findByIdAndLeague_IdAndSeason_Year(teamId, leagueId, seasonYear);

        if (team.isEmpty()) {
            log.info("Team {} not found locally. Syncing teams for league {} season {}",
                    teamId, leagueId, seasonYear);
            syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);
            team = teamRepository.findByIdAndLeague_IdAndSeason_Year(teamId, leagueId, seasonYear);
        }

        return team.map(internalTeamMapper::toTeamDTO)
                .orElseThrow(() -> new RuntimeException(
                        "Team " + teamId + " not found in league " + leagueId + " season " + seasonYear));
    }

    /**
     * Returnează lista de echipe pentru o ligă și sezon.
     * Dacă nu există local, sincronizează de la API.
     */
    public List<TeamDTO> getTeamsByLeagueIdAndSeasonYear(Integer leagueId, Integer seasonYear) {
        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + leagueId));

        var teams = teamRepository.findByLeague_IdAndSeason_Year(leagueId, seasonYear);

        if (teams.isEmpty()) {
            log.info("No teams found for league {} season {}. Syncing from API.", leagueId, seasonYear);
            syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);
            teams = teamRepository.findByLeague_IdAndSeason_Year(leagueId, seasonYear);
        }

        return internalTeamMapper.toTeamDTOS(teams);
    }

    // SYNCHRONIZATION METHODS

    /**
     * Sincronizează echipele pentru o ligă și sezon de la API-ul extern.
     * IMPORTANT: leagueId aici este EXTERNAL ID (id-ul din API), nu id-ul intern.
     */
    public void syncTeamByLeagueAndSeason(Integer externalLeagueId, Integer seasonYear) {
        // Găsește liga după externalId
        var league = leagueRepository.findByExternalId(externalLeagueId)
                .orElseThrow(() -> new IllegalStateException(
                        "League with external id " + externalLeagueId + " not found. Sync leagues first."));

        // Găsește sezonul
        var season = seasonRepository.findByLeagueAndYear(league, seasonYear)
                .orElseThrow(() -> new IllegalStateException(
                        "Season " + seasonYear + " not found for league " + league.getName() +
                        ". Sync leagues first."));

        try {
            var response = footballApiClient.getTeamsByLeagueIdAndSeasonYear(externalLeagueId, seasonYear);

            if (response == null || response.response() == null || response.response().isEmpty()) {
                log.warn("No teams returned from API for league {} season {}", externalLeagueId, seasonYear);
                return;
            }

            for (ExternalTeamDTO dto : response.response()) {
                VenueEntity venue = syncVenue(dto.venue());
                TeamEntity team = syncTeam(dto.team(), season, league, venue);
                teamRepository.save(team);
            }

            log.info("Successfully synced {} teams for league {} season {}",
                    response.response().size(), league.getName(), seasonYear);
        } catch (Exception e) {
            log.error("Failed to sync teams for league {} season {}", externalLeagueId, seasonYear, e);
            throw new RuntimeException("Failed to sync teams", e);
        }
    }

    // PRIVATE HELPER METHODS

    private VenueEntity syncVenue(ExternalVenueDTO external) {
        if (external == null || external.id() == null) {
            log.warn("Venue data is null or incomplete");
            return null;
        }

        var existing = venueRepository.findByExternalId(external.id());

        if (existing.isPresent()) {
            var venue = existing.get();
            teamMapper.updateVenueEntity(venue, external);
            return venue;
        }

        var newVenue = teamMapper.toVenueEntity(external);
        return venueRepository.save(newVenue);
    }

    private TeamEntity syncTeam(ExternalTeamInfoDTO external, SeasonEntity season,
                                LeagueEntity league, VenueEntity venue) {
        var existing = teamRepository.findByExternalIdAndLeague_IdAndSeason_Year(
                external.id(),
                league.getId(),
                season.getYear()
        );

        if (existing.isPresent()) {
            var team = existing.get();
            team.setVenue(venue);
            team.setLeague(league);
            team.setSeason(season);
            teamMapper.updateTeamEntity(team, external);
            return team;
        }

        var newTeam = teamMapper.toTeamEntity(external);
        newTeam.setVenue(venue);
        newTeam.setSeason(season);
        newTeam.setLeague(league);
        return newTeam;
    }


}
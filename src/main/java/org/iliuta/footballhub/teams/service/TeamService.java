package org.iliuta.footballhub.teams.service;

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
                       FootballApiClient footballApiClient, InternalTeamMapper internalTeamMapper) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.teamRepository = teamRepository;
        this.venueRepository = venueRepository;
        this.teamMapper = teamMapper;
        this.footballApiClient = footballApiClient;
        this.internalTeamMapper = internalTeamMapper;
    }

    public TeamDTO getTeamByIdInContext(Integer teamId, Integer leagueId, Integer seasonYear) {
        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found: " + leagueId));

        var team = teamRepository.findByIdAndLeague_IdAndSeason_Year(teamId, leagueId, seasonYear);

        if (team.isEmpty()) {
            syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);
            team = teamRepository.findByIdAndLeague_IdAndSeason_Year(teamId, leagueId, seasonYear);
        }

        return team.map(internalTeamMapper::toTeamDTO)
                .orElseThrow(() -> new RuntimeException(
                        "Team " + teamId + " not found in league " + leagueId + " season " + seasonYear));
    }

    public List<TeamDTO> getTeamsByLeagueIdAndSeasonYear(Integer leagueId, Integer seasonYear) {
        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found!"));
        var teams = teamRepository.findByLeague_IdAndSeason_Year(leagueId, seasonYear);

        if (teams.isEmpty()) {
            syncTeamByLeagueAndSeason(league.getExternalId(), seasonYear);
            teams = teamRepository.findByLeague_IdAndSeason_Year(leagueId, seasonYear);
        }

        return internalTeamMapper.toTeamDTOS(teams);
    }

    public void syncTeamByLeagueAndSeason(Integer leagueId, Integer seasonYear) {
        var league = leagueRepository
                .findByExternalId(leagueId)
                .orElseThrow(() -> new IllegalStateException("League not found-sync leagues first"));
        var season = seasonRepository
                .findByLeagueAndYear(league, seasonYear)
                .orElseThrow(() -> new IllegalStateException("Season not found-sync leagues first"));

        var response =
                footballApiClient.getTeamsByLeagueIdAndSeasonYear(leagueId, seasonYear);

        for (ExternalTeamDTO dto : response.response()) {
            VenueEntity venue = syncVenue(dto.venue());
            TeamEntity team = syncTeam(dto.team(), season, league, venue);
            teamRepository.save(team);
        }
    }

    private VenueEntity syncVenue(ExternalVenueDTO external) {
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
        var existing = teamRepository
                .findByExternalIdAndLeague_IdAndSeason_Year(
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

package org.iliuta.footballhub.team.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.team.ExternalTeamDTO;
import org.iliuta.footballhub.client.dto.team.ExternalTeamInfoDTO;
import org.iliuta.footballhub.client.dto.team.ExternalVenueDTO;
import org.iliuta.footballhub.league.LeagueEntity;
import org.iliuta.footballhub.league.LeagueRepository;
import org.iliuta.footballhub.league.SeasonEntity;
import org.iliuta.footballhub.league.SeasonRepository;
import org.iliuta.footballhub.team.TeamEntity;
import org.iliuta.footballhub.team.TeamRepository;
import org.iliuta.footballhub.team.VenueEntity;
import org.iliuta.footballhub.team.VenueRepository;
import org.iliuta.footballhub.team.mapper.ExternalTeamMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamService {

    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final VenueRepository venueRepository;
    private final ExternalTeamMapper teamMapper;
    private final FootballApiClient footballApiClient;


    public TeamService(LeagueRepository leagueRepository,
                       SeasonRepository seasonRepository,
                       TeamRepository teamRepository,
                       VenueRepository venueRepository,
                       ExternalTeamMapper teamMapper,
                       FootballApiClient footballApiClient) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.teamRepository = teamRepository;
        this.venueRepository = venueRepository;
        this.teamMapper = teamMapper;
        this.footballApiClient = footballApiClient;
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
                .findByExternalId(external.id());

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

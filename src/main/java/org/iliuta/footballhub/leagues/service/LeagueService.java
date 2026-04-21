package org.iliuta.footballhub.leagues.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.leagues.ExternalLeagueInfoDTO;
import org.iliuta.footballhub.client.dto.leagues.ExternalSeasonDTO;
import org.iliuta.footballhub.countries.CountryEntity;
import org.iliuta.footballhub.countries.service.CountryService;
import org.iliuta.footballhub.leagues.LeagueEntity;
import org.iliuta.footballhub.leagues.LeagueRepository;
import org.iliuta.footballhub.leagues.SeasonEntity;
import org.iliuta.footballhub.leagues.SeasonRepository;
import org.iliuta.footballhub.leagues.dto.LeagueDTO;
import org.iliuta.footballhub.leagues.dto.SeasonDTO;
import org.iliuta.footballhub.leagues.mapper.ExternalLeagueMapper;
import org.iliuta.footballhub.leagues.mapper.InternalLeagueMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class LeagueService {

    private final SeasonRepository seasonRepository;
    private final CountryService countryService;
    private final LeagueRepository leagueRepository;
    private final ExternalLeagueMapper externalLeagueMapper;
    private final InternalLeagueMapper internalLeagueMapper;
    private final FootballApiClient footballApiClient;

    public LeagueService(
            SeasonRepository seasonRepository, CountryService countryService,
            LeagueRepository leagueRepository,
            ExternalLeagueMapper externalLeagueMapper, InternalLeagueMapper internalLeagueMapper,
            FootballApiClient footballApiClient) {
        this.seasonRepository = seasonRepository;
        this.countryService = countryService;
        this.leagueRepository = leagueRepository;
        this.externalLeagueMapper = externalLeagueMapper;
        this.internalLeagueMapper = internalLeagueMapper;
        this.footballApiClient = footballApiClient;
    }

    public void syncLeaguesByCountry(String countryCode) {
        var response = footballApiClient.getLeaguesByCountry(countryCode);
        for (var dto : response.response()) {
            CountryEntity country = countryService.syncCountry(dto.country());
            LeagueEntity league = syncLeague(dto.league(), country);
            for (var seasonDto : dto.seasons()) {
                syncSeason(seasonDto, league);
            }
        }
    }

    public List<LeagueDTO> getLeaguesByCountryCode(String code) {
        var leagues = leagueRepository.findByCountry_Code(code);

        if (leagues.isEmpty()) {
            syncLeaguesByCountry(code);
            leagues = leagueRepository.findByCountry_Code(code);
        }

        return internalLeagueMapper.toLeagueDTOs(leagues);
    }

    public List<SeasonDTO> getSeasonsByLeagueId(Integer id) {
        var seasons = seasonRepository.findByLeague_Id(id);

        return internalLeagueMapper.toSeasonDTOs(seasons);
    }

    private SeasonEntity syncSeason(ExternalSeasonDTO external, LeagueEntity league) {

        var existing =
                seasonRepository.findByLeagueAndYear(league, external.year());

        if (existing.isPresent()) {
            var season = existing.get();
            externalLeagueMapper.updateSeasonEntity(season, external);
            season.setLeague(league);

            return season;
        }

        var newSeason = externalLeagueMapper.toSeasonEntity(external);
        newSeason.setLeague(league);

        return seasonRepository.save(newSeason);
    }

    private LeagueEntity syncLeague(ExternalLeagueInfoDTO external, CountryEntity country) {

        var existing = leagueRepository.findByExternalId(external.id());

        if (existing.isPresent()) {
            var league = existing.get();
            externalLeagueMapper.updateLeagueEntity(league, external);
            league.setCountry(country);

            return league;
        }

        var newLeague = externalLeagueMapper.toLeagueEntity(external);
        newLeague.setCountry(country);

        return leagueRepository.save(newLeague);
    }


}

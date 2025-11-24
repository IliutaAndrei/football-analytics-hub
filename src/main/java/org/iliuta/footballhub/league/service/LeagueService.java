package org.iliuta.footballhub.league.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.league.ExternalCountryDTO;
import org.iliuta.footballhub.client.dto.league.ExternalLeagueDTO;
import org.iliuta.footballhub.client.dto.league.ExternalLeagueInfoDTO;
import org.iliuta.footballhub.client.dto.league.ExternalSeasonDTO;
import org.iliuta.footballhub.country.CountryEntity;
import org.iliuta.footballhub.country.CountryRepository;
import org.iliuta.footballhub.league.LeagueEntity;
import org.iliuta.footballhub.league.LeagueRepository;
import org.iliuta.footballhub.league.SeasonEntity;
import org.iliuta.footballhub.league.SeasonRepository;
import org.iliuta.footballhub.league.mapper.ExternalLeagueMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LeagueService {

    private final CountryRepository countryRepository;
    private final SeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;
    private final ExternalLeagueMapper externalLeagueMapper;
    private final FootballApiClient footballApiClient;

    public LeagueService(CountryRepository countryRepository,
                         SeasonRepository seasonRepository,
                         LeagueRepository leagueRepository,
                         ExternalLeagueMapper externalLeagueMapper,
                         FootballApiClient footballApiClient) {
        this.countryRepository = countryRepository;
        this.seasonRepository = seasonRepository;
        this.leagueRepository = leagueRepository;
        this.externalLeagueMapper = externalLeagueMapper;
        this.footballApiClient = footballApiClient;
    }

    public void syncLeaguesByCountry(String countryCode) {
        var response = footballApiClient.getLeaguesByCountry(countryCode);
        for (var dto : response.response()) {
            CountryEntity country = syncCountry(dto.country());
            LeagueEntity league = syncLeague(dto.league(), country);
            for (var seasonDto : dto.seasons()) {
                syncSeason(seasonDto, league);
            }
        }
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

    private CountryEntity syncCountry(ExternalCountryDTO external) {

        var existing = countryRepository.findByCode(external.code());

        if (existing.isPresent()) {
            var country = existing.get();
            externalLeagueMapper.updateCountryEntity(country, external);

            return country;
        }

        var newCountry = externalLeagueMapper.toCountryEntity(external);
        return countryRepository.save(newCountry);
    }
}

package org.iliuta.footballhub.leagues.service;

import lombok.extern.slf4j.Slf4j;
import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.leagues.ExternalLeagueDTO;
import org.iliuta.footballhub.client.dto.leagues.ExternalLeagueInfoDTO;
import org.iliuta.footballhub.client.dto.leagues.ExternalSeasonDTO;
import org.iliuta.footballhub.countries.CountryEntity;
import org.iliuta.footballhub.countries.service.CountryService;
import org.iliuta.footballhub.leagues.LeagueEntity;
import org.iliuta.footballhub.leagues.LeagueRepository;
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
@Slf4j
public class LeagueService {

    private final SeasonRepository seasonRepository;
    private final CountryService countryService;
    private final LeagueRepository leagueRepository;
    private final ExternalLeagueMapper externalLeagueMapper;
    private final InternalLeagueMapper internalLeagueMapper;
    private final FootballApiClient footballApiClient;

    public LeagueService(
            SeasonRepository seasonRepository,
            CountryService countryService,
            LeagueRepository leagueRepository,
            ExternalLeagueMapper externalLeagueMapper,
            InternalLeagueMapper internalLeagueMapper,
            FootballApiClient footballApiClient) {
        this.seasonRepository = seasonRepository;
        this.countryService = countryService;
        this.leagueRepository = leagueRepository;
        this.externalLeagueMapper = externalLeagueMapper;
        this.internalLeagueMapper = internalLeagueMapper;
        this.footballApiClient = footballApiClient;
    }

    // PUBLIC API METHODS

    public List<LeagueDTO> getLeaguesByCountryCode(String countryCode) {
        var leagues = leagueRepository.findByCountry_Code(countryCode);

        if (leagues.isEmpty()) {
            log.info("No leagues found for country: {}. Syncing from external API.", countryCode);
            syncLeaguesByCountry(countryCode);
            leagues = leagueRepository.findByCountry_Code(countryCode);
        }

        return internalLeagueMapper.toLeagueDTOs(leagues);
    }

    public List<SeasonDTO> getSeasonsByLeagueId(Integer leagueId) {
        var league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + leagueId));

        var seasons = seasonRepository.findByLeague_Id(leagueId);

        if (seasons.isEmpty()) {
            log.warn("No seasons found for league: {} ({}). This might indicate missing data.",
                    league.getName(), leagueId);
        }

        return internalLeagueMapper.toSeasonDTOs(seasons);
    }

    // SYNCHRONIZATION METHODS

    public void syncLeaguesByCountry(String countryCode) {
        try {
            var response = footballApiClient.getLeaguesByCountry(countryCode);

            if (response == null || response.response() == null || response.response().isEmpty()) {
                log.warn("No leagues returned from API for country: {}", countryCode);
                return;
            }

            for (var leagueData : response.response()) {
                syncSingleLeague(leagueData);
            }

            log.info("Successfully synced {} leagues for country: {}",
                    response.response().size(), countryCode);
        } catch (Exception e) {
            log.error("Failed to sync leagues for country: {}", countryCode, e);
            throw new RuntimeException("Failed to sync leagues for country: " + countryCode, e);
        }
    }

    // PRIVATE HELPER METHODS

    private void syncSingleLeague(ExternalLeagueDTO leagueData) {
        CountryEntity country = countryService.syncCountry(leagueData.country());
        LeagueEntity league = syncLeague(leagueData.league(), country);

        for (var seasonDto : leagueData.seasons()) {
            syncSeason(seasonDto, league);
        }
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

    private void syncSeason(ExternalSeasonDTO external, LeagueEntity league) {
        var existing = seasonRepository.findByLeagueAndYear(league, external.year());

        if (existing.isPresent()) {
            var season = existing.get();
            externalLeagueMapper.updateSeasonEntity(season, external);
            season.setLeague(league);
            return;
        }

        var newSeason = externalLeagueMapper.toSeasonEntity(external);
        newSeason.setLeague(league);
        seasonRepository.save(newSeason);
    }
}

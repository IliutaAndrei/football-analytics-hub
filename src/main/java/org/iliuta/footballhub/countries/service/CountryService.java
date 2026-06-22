package org.iliuta.footballhub.countries.service;

import lombok.extern.slf4j.Slf4j;
import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.countries.ExternalCountryDTO;
import org.iliuta.footballhub.countries.CountryEntity;
import org.iliuta.footballhub.countries.CountryRepository;
import org.iliuta.footballhub.countries.dto.CountryDTO;
import org.iliuta.footballhub.countries.mapper.ExternalCountryMapper;
import org.iliuta.footballhub.countries.mapper.InternalCountryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CountryService {

    private final FootballApiClient footballApiClient;
    private final CountryRepository countryRepository;
    private final ExternalCountryMapper externalCountryMapper;
    private final InternalCountryMapper internalCountryMapper;

    public CountryService(
            FootballApiClient footballApiClient,
            CountryRepository countryRepository,
            ExternalCountryMapper externalCountryMapper,
            InternalCountryMapper internalCountryMapper) {
        this.footballApiClient = footballApiClient;
        this.countryRepository = countryRepository;
        this.externalCountryMapper = externalCountryMapper;
        this.internalCountryMapper = internalCountryMapper;
    }

    // PUBLIC API METHODS

    public List<CountryDTO> getAllCountries() {
        return internalCountryMapper.toCountryDTOs(countryRepository.findAll());
    }

    public CountryEntity syncCountry(ExternalCountryDTO external) {
        var existing = countryRepository.findByCode(external.code());

        if (existing.isPresent()) {
            var country = existing.get();
            externalCountryMapper.updateCountryEntity(country, external);
            return country;
        }

        var newCountry = externalCountryMapper.toCountryEntity(external);
        return countryRepository.save(newCountry);
    }

    // SYNCHRONIZATION METHODS

    @EventListener(ApplicationReadyEvent.class)
    public void syncOnStartup() {
        long count = countryRepository.count();
        if (count == 0) {
            log.info("No countries found in database. Syncing from external API.");
            syncAllCountries();
            log.info("Countries sync completed on startup.");
        } else {
            log.info("Countries already in database ({}). Skipping sync.", count);
        }
    }

    public void syncAllCountries() {
        try {
            var response = footballApiClient.getAllCountries();

            if (response == null || response.response() == null || response.response().isEmpty()) {
                log.warn("No countries returned from external API.");
                return;
            }

            for (ExternalCountryDTO country : response.response()) {
                syncCountry(country);
            }

            log.info("Successfully synced {} countries.", response.response().size());
        } catch (Exception e) {
            log.error("Failed to sync countries.", e);
            throw new RuntimeException("Failed to sync countries.", e);
        }
    }
}
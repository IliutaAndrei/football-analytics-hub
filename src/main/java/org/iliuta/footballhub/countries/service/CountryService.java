package org.iliuta.footballhub.countries.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.countries.ExternalCountryDTO;
import org.iliuta.footballhub.countries.CountryEntity;
import org.iliuta.footballhub.countries.CountryRepository;
import org.iliuta.footballhub.countries.dto.CountryDTO;
import org.iliuta.footballhub.countries.mapper.ExternalCountryMapper;
import org.iliuta.footballhub.countries.mapper.InternalCountryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountryService {

    private final FootballApiClient footballApiClient;
    private final CountryRepository countryRepository;
    private final ExternalCountryMapper externalCountryMapper;
    private final InternalCountryMapper internalCountryMapper;

    public CountryService(FootballApiClient footballApiClient, CountryRepository countryRepository , ExternalCountryMapper externalCountryMapper, InternalCountryMapper internalCountryMapper) {
        this.footballApiClient = footballApiClient;
        this.countryRepository = countryRepository;
        this.externalCountryMapper = externalCountryMapper;
        this.internalCountryMapper = internalCountryMapper;
    }

    public void syncAllCountries() {
        var response = footballApiClient.getAllCountries();
        for (ExternalCountryDTO country : response.response()) {
            syncCountry(country);
        }
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

    public List<CountryDTO> getAllCountries() {
        return internalCountryMapper.toCountryDTOs(countryRepository.findAll());
    }
}

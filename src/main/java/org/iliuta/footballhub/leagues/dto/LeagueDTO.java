package org.iliuta.footballhub.leagues.dto;

import org.iliuta.footballhub.countries.dto.CountryDTO;

public record LeagueDTO(
        Integer id,
        Integer externalId,
        String name,
        String type,
        String logo,
        CountryDTO country
) { }

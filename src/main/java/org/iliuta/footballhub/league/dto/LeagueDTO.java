package org.iliuta.footballhub.league.dto;

import org.iliuta.footballhub.country.dto.CountryDTO;

public record LeagueDTO(
        Integer id,
        Integer externalId,
        String name,
        String type,
        String logo,
        CountryDTO country
) { }

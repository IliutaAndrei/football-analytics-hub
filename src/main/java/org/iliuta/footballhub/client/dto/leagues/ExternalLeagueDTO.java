package org.iliuta.footballhub.client.dto.leagues;

import java.util.List;

public record ExternalLeagueDTO(
        ExternalLeagueInfoDTO league,
        ExternalCountryDTO country,
        List<ExternalSeasonDTO> seasons
) { }

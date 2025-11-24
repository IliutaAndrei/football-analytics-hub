package org.iliuta.footballhub.client.dto.league;

import java.util.List;

public record ExternalLeagueDTO(
        ExternalLeagueInfoDTO league,
        ExternalCountryDTO country,
        List<ExternalSeasonDTO> seasons
) { }

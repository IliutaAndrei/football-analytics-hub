package org.iliuta.footballhub.client.dto.leagues;

import java.util.List;
import org.iliuta.footballhub.client.dto.countries.ExternalCountryDTO;

public record ExternalLeagueDTO(
        ExternalLeagueInfoDTO league,
        ExternalCountryDTO country,
        List<ExternalSeasonDTO> seasons
) { }

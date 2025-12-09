package org.iliuta.footballhub.client.dto.standings;

import java.util.List;

public record ExternalStandingsLeagueDTO(
        Integer id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season,

        List<List<ExternalStandingsSummaryDTO>> standings
) {
}

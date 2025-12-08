package org.iliuta.footballhub.client.dto.standings;

import java.util.List;

public record ExternalStandingsDTO(
        ExternalStandingsLeagueDTO league,
        List<List<ExternalStandingsSummaryDTO>> standings
) {
}

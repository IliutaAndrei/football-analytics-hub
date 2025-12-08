package org.iliuta.footballhub.client.dto.standings;

public record ExternalStandingsLeagueDTO(
        Integer id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season
) {
}

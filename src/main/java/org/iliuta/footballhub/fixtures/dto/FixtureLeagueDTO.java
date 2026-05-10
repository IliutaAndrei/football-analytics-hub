package org.iliuta.footballhub.fixtures.dto;

public record FixtureLeagueDTO(
        Integer id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season,
        String round
) {
}

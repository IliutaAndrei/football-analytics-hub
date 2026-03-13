package org.iliuta.footballhub.teams.statistics.dto;

public record FixtureDTO(
        FixtureStatDTO played,
        FixtureStatDTO wins,
        FixtureStatDTO draws,
        FixtureStatDTO loses
) {
}

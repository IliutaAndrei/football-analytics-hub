package org.iliuta.footballhub.team.statistics.dto;

public record FixtureDTO(
        FixtureStatDTO played,
        FixtureStatDTO wins,
        FixtureStatDTO draws,
        FixtureStatDTO loses
) {
}

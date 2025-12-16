package org.iliuta.footballhub.fixtures.dto;

import java.util.List;

public record FixtureResponseDTO(
    Integer leagueId,
    Integer season,
    Integer teamId,
    List<FixtureDTO> playedFixtures,
    List<FixtureDTO> upcomingFixtures
) {
}

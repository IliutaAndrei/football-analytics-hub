package org.iliuta.footballhub.fixtures.dto;

public record FixtureDTO(
        Integer fixtureId,
        String date,
        String status,
        Integer elapsed,
        Integer leagueId,
        Integer season,
        String round,
        FixtureTeamDTO homeTeam,
        FixtureTeamDTO awayTeam,
        FixtureScoreDTO score
) {
}

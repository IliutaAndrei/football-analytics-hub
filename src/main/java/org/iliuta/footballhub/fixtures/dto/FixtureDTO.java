package org.iliuta.footballhub.fixtures.dto;


public record FixtureDTO(

        FixtureInfoDTO fixture,
        FixtureLeagueDTO league,
        FixtureTeamsDTO teams,
        FixtureGoalsDTO goals,
        FixtureScoreDTO score
) {
}

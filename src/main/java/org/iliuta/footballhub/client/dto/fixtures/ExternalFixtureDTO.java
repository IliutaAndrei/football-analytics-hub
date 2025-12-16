package org.iliuta.footballhub.client.dto.fixtures;

public record ExternalFixtureDTO(
        ExternalFixtureInfoDTO fixture,
        ExternalFixtureLeagueDTO league,
        ExternalFixtureTeamsDTO teams,
        ExternalGoalsDTO goals,
        ExternalScoreDTO score
) {
}

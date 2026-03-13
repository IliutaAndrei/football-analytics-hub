package org.iliuta.footballhub.fixtures.dto;

public record FixtureScoreDTO(
        ScoreSummaryDTO halfTime,
        ScoreSummaryDTO fullTime
) {
}

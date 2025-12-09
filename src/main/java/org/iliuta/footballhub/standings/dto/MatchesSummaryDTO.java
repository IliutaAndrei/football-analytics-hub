package org.iliuta.footballhub.standings.dto;

public record MatchesSummaryDTO(
        Integer played,
        Integer win,
        Integer draw,
        Integer lose,
        GoalsDTO goals
) {
}

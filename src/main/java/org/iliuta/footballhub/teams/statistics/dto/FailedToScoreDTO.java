package org.iliuta.footballhub.teams.statistics.dto;

public record FailedToScoreDTO(
        Integer home,
        Integer away,
        Integer total
) {
}

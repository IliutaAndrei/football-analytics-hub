package org.iliuta.footballhub.team.statistics.dto;

public record FailedToScoreDTO(
        Integer home,
        Integer away,
        Integer total
) {
}

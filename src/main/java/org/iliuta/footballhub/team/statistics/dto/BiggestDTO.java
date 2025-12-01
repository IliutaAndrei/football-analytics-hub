package org.iliuta.footballhub.team.statistics.dto;

public record BiggestDTO(
    BiggestMatchResultDTO wins,
    BiggestMatchResultDTO loses,
    BiggestGoalDTO goals
) {
}

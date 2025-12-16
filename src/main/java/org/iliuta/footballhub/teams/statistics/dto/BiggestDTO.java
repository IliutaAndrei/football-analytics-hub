package org.iliuta.footballhub.teams.statistics.dto;

public record BiggestDTO(
    BiggestMatchResultDTO wins,
    BiggestMatchResultDTO loses,
    BiggestGoalDTO goals
) {
}

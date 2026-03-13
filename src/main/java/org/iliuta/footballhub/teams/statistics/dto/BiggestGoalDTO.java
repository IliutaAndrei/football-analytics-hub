package org.iliuta.footballhub.teams.statistics.dto;

public record BiggestGoalDTO(
        BiggestGoalCountDTO forGoals,
        BiggestGoalCountDTO againstGoals
) {
}

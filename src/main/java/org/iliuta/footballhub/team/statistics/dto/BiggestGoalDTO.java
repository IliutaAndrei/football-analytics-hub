package org.iliuta.footballhub.team.statistics.dto;

public record BiggestGoalDTO(
        BiggestGoalCountDTO forGoals,
        BiggestGoalCountDTO againstGoals
) {
}

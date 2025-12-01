package org.iliuta.footballhub.team.statistics.dto;

public record GoalDTO(
        GoalValueDTO forGoals,
        GoalValueDTO againstGoals
) {
}

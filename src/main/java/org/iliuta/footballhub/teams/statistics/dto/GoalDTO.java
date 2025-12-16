package org.iliuta.footballhub.teams.statistics.dto;

public record GoalDTO(
        GoalValueDTO forGoals,
        GoalValueDTO againstGoals
) {
}

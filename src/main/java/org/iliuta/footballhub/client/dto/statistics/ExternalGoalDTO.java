package org.iliuta.footballhub.client.dto.statistics;

public record ExternalGoalDTO(
        ExternalGoalValueDTO forGoals,
        ExternalGoalValueDTO againstGoals
) {
}

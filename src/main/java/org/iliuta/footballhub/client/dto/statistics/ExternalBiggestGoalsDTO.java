package org.iliuta.footballhub.client.dto.statistics;

public record ExternalBiggestGoalsDTO(
        ExternalHomeAwayIntDTO forGoals,
        ExternalHomeAwayIntDTO againstGoals
) {
}

package org.iliuta.footballhub.client.dto.statistics;

public record ExternalBiggestDTO(
      ExternalStreakDTO streak,
      ExternalHomeAwayStringDTO wins,
      ExternalHomeAwayStringDTO loses,
      ExternalBiggestGoalsDTO goals
) {
}

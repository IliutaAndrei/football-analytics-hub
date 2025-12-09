package org.iliuta.footballhub.standings.dto;

public record StandingDTO(
      Integer rank,
      TeamDTO team,
      Integer points,
      Integer goalsDiff,
      String form,
      MatchesSummaryDTO summary
) {
}

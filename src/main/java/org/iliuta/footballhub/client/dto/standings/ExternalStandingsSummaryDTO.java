package org.iliuta.footballhub.client.dto.standings;

public record ExternalStandingsSummaryDTO(
        Integer rank,
        ExternalTeamSummaryDTO team,
        Integer points,
        Integer goalsDiff,
        ExternalTeamMatchStatsDTO all

) {
}

package org.iliuta.footballhub.client.dto.statistics;

public record ExternalFixtureDTO(
        ExternalStatSummaryDTO played,
        ExternalStatSummaryDTO wins,
        ExternalStatSummaryDTO draws,
        ExternalStatSummaryDTO loses
) {
}

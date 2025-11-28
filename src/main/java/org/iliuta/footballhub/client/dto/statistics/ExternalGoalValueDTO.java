package org.iliuta.footballhub.client.dto.statistics;

public record ExternalGoalValueDTO(
        ExternalStatSummaryDTO total,
        ExternalStatSummaryDTO average,
        ExternalMinuteDTO minute,
        ExternalUnderOverDTO underOver
) {
}

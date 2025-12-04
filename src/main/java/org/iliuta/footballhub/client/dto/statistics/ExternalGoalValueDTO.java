package org.iliuta.footballhub.client.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalGoalValueDTO(
        ExternalStatSummaryTotalDTO total,
        ExternalStatSummaryAverageDTO average,
        ExternalMinuteDTO minute,
        @JsonProperty("under_over")
        ExternalUnderOverDTO underOver
) {
}

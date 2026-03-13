package org.iliuta.footballhub.client.dto.fixtures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalScoreDTO(
        @JsonProperty("halftime")
        ExternalScoreSummaryDTO halfTime,
        @JsonProperty("fulltime")
        ExternalScoreSummaryDTO fullTime
) {
}

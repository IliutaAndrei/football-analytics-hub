package org.iliuta.footballhub.client.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalTeamStatisticsResponseDTO(
        @JsonProperty("response")
        ExternalTeamStatisticsDTO response
) {
}

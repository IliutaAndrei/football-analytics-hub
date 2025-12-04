package org.iliuta.footballhub.client.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalGoalDTO(
        @JsonProperty("for")
        ExternalGoalValueDTO forGoals,

        @JsonProperty("against")
        ExternalGoalValueDTO againstGoals
) {
}

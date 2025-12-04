package org.iliuta.footballhub.client.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalBiggestGoalsDTO(
        @JsonProperty("for")
        ExternalHomeAwayIntDTO forGoals,

        @JsonProperty("against")
        ExternalHomeAwayIntDTO againstGoals
) {
}

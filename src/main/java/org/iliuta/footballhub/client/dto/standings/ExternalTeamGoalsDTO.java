package org.iliuta.footballhub.client.dto.standings;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalTeamGoalsDTO(
        @JsonProperty("for")
        Integer forGoals,
        Integer against
) {
}

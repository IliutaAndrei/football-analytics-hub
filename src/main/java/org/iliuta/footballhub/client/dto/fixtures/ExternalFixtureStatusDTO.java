package org.iliuta.footballhub.client.dto.fixtures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalFixtureStatusDTO(
        @JsonProperty("long")
        String statusLong,
        Integer elapsed,
        @JsonProperty("extra")
        Integer extraTime
) {
}

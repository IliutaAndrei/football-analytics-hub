package org.iliuta.footballhub.client.dto.fixtures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalFixtureStatusDTO(
        @JsonProperty("short")
        String statusShort,
        Integer elapsed
) {
}

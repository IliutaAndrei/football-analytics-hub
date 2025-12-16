package org.iliuta.footballhub.client.dto.fixtures;

public record ExternalFixtureInfoDTO(
        Integer id,
        String date,
        ExternalFixtureStatusDTO status
) {
}

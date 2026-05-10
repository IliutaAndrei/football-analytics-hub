package org.iliuta.footballhub.client.dto.fixtures;

public record ExternalFixtureInfoDTO(
        Integer id,
        String referee,
        String date,
        ExternalFixtureVenueDTO venue,
        ExternalFixtureStatusDTO status
) {
}

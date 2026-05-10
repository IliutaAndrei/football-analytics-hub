package org.iliuta.footballhub.fixtures.dto;

public record FixtureInfoDTO(
        Integer id,
        String referee,
        String date,
        FixtureVenueDTO venue,
        FixtureStatusDTO status
) {
}

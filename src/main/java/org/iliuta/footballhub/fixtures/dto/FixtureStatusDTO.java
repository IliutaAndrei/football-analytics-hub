package org.iliuta.footballhub.fixtures.dto;

public record FixtureStatusDTO(
        String statusLong,
        Integer elapsed,
        Integer extraTime
) {
}

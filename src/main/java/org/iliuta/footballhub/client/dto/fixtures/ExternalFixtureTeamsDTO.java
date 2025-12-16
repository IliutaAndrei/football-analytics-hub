package org.iliuta.footballhub.client.dto.fixtures;

public record ExternalFixtureTeamsDTO(
        ExternalFixtureTeamDTO home,
        ExternalFixtureTeamDTO away
) {
}

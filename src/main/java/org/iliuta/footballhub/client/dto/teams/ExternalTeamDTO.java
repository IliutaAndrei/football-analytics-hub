package org.iliuta.footballhub.client.dto.teams;

public record ExternalTeamDTO(
    ExternalTeamInfoDTO team,
    ExternalVenueDTO venue
) { }

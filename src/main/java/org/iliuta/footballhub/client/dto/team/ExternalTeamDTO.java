package org.iliuta.footballhub.client.dto.team;

public record ExternalTeamDTO(
    ExternalTeamInfoDTO team,
    ExternalVenueDTO venue
) { }

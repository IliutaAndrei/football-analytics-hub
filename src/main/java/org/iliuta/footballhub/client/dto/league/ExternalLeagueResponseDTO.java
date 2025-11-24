package org.iliuta.footballhub.client.dto.league;

import java.util.List;

public record ExternalLeagueResponseDTO(
        List<ExternalLeagueDTO> response
) { }

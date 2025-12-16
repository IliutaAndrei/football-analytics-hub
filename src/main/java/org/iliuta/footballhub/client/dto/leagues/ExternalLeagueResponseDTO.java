package org.iliuta.footballhub.client.dto.leagues;

import java.util.List;

public record ExternalLeagueResponseDTO(
        List<ExternalLeagueDTO> response
) { }

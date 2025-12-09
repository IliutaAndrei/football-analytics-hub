package org.iliuta.footballhub.client.dto.standings;

import java.util.List;

public record ExternalStandingsResponseDTO(
        List<ExternalStandingsDTO> response
) {
}

package org.iliuta.footballhub.client.dto.fixtures.statistics;

import java.util.List;

public record ExternalFixturesStatisticsResponseDTO(
        List<ExternalFixturesStatisticsDTO> response
) {
}

package org.iliuta.footballhub.fixtures.statistics.dto;

import java.util.List;

public record FixturesStatisticsResponseDTO(
        List<FixturesStatisticsDTO> response
) {
}

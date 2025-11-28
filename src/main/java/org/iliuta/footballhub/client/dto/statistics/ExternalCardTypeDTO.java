package org.iliuta.footballhub.client.dto.statistics;

import java.util.Map;

public record ExternalCardTypeDTO(
    Map<String, ExternalCardPeriodStatsDTO> intervals
) {
}

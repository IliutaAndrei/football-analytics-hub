package org.iliuta.footballhub.client.dto.statistics;

import java.util.Map;

public record ExternalMinuteDTO(
        Map<String, ExternalMinuteStatDTO> intervals
) {
}

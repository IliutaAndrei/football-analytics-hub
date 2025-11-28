package org.iliuta.footballhub.client.dto.statistics;

import java.util.Map;

public record ExternalUnderOverDTO(
        Map<String, ExternalUnderOverStatDTO> values
) {
}

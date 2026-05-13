package org.iliuta.footballhub.client.dto.players;

import java.util.List;

public record ExternalPlayerDTO(
        ExternalPlayerInfoDTO  player,
        List<ExternalPlayerStatisticsDTO> statistics
) {
}

package org.iliuta.footballhub.fixtures.players.dto;

import java.util.List;

public record PlayerDTO(
        PlayerInfoDTO player,
        List<PlayerStatisticsDTO> statistics
) {
}

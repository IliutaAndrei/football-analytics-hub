package org.iliuta.footballhub.players.statistics.dto;

public record TacklesDTO(
        Integer total,
        Integer blocks,
        Integer interceptions
) {
}

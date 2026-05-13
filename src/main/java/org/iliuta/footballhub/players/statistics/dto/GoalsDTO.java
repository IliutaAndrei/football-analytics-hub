package org.iliuta.footballhub.players.statistics.dto;

public record GoalsDTO(
        Integer total,
        Integer conceded,
        Integer assists,
        Integer saved
) {
}

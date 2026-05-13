package org.iliuta.footballhub.client.dto.players;

public record ExternalGoalsDTO(
        Integer total,
        Integer conceded,
        Integer assists,
        Integer saves
) {
}

package org.iliuta.footballhub.client.dto.players;

public record ExternalTacklesDTO(
        Integer total,
        Integer blocks,
        Integer interceptions
) {
}

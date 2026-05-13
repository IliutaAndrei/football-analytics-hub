package org.iliuta.footballhub.client.dto.players;

public record ExternalDribblesDTO(
        Integer attempts,
        Integer success,
        Integer past
) {
}

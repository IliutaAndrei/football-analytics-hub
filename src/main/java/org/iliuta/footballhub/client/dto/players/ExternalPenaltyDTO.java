package org.iliuta.footballhub.client.dto.players;

public record ExternalPenaltyDTO(
        Integer won,
        Integer commited,
        Integer scored,
        Integer missed,
        Integer saved
) {
}

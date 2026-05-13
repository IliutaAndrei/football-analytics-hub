package org.iliuta.footballhub.players.statistics.dto;

public record PenaltyDTO(
        Integer won,
        Integer commited,
        Integer scored,
        Integer missed,
        Integer saved
) {
}

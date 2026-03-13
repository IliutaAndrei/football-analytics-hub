package org.iliuta.footballhub.teams.statistics.dto;

public record PenaltyDTO(
        PenaltyStatusDTO scored,
        PenaltyStatusDTO missed,
        Integer total
) {
}

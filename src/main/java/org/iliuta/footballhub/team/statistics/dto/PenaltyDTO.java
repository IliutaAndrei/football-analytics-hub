package org.iliuta.footballhub.team.statistics.dto;

public record PenaltyDTO(
        PenaltyStatusDTO scored,
        PenaltyStatusDTO missed,
        Integer total
) {
}

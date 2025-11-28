package org.iliuta.footballhub.client.dto.statistics;

public record ExternalPenaltyDTO(
        ExternalPenaltyStatusDTO scored,
        ExternalPenaltyStatusDTO missed,
        Integer total
) {
}

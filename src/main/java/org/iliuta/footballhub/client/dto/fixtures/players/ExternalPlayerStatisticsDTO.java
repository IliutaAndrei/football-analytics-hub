package org.iliuta.footballhub.client.dto.fixtures.players;

import org.iliuta.footballhub.client.dto.players.*;

public record ExternalPlayerStatisticsDTO(
        ExternalGamesDTO games,
        ExternalShotsDTO shots,
        ExternalGoalsDTO goals,
        ExternalPassesDTO passes,
        ExternalTacklesDTO tackles,
        ExternalDuelsDTO duels,
        ExternalDribblesDTO dribbles,
        ExternalFoulsDTO fouls,
        ExternalCardsDTO cards,
        ExternalPenaltyDTO penalty
) {
}

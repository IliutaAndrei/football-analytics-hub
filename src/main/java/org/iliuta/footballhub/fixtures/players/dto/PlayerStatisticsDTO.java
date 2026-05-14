package org.iliuta.footballhub.fixtures.players.dto;

import org.iliuta.footballhub.players.statistics.dto.*;

public record PlayerStatisticsDTO(
        GamesDTO games,
        ShotsDTO shots,
        GoalsDTO goals,
        PassesDTO passes,
        TacklesDTO tackles,
        DuelsDTO duels,
        DribblesDTO dribbles,
        FoulsDTO fouls,
        CardsDTO cards,
        PenaltyDTO penalty
) {
}

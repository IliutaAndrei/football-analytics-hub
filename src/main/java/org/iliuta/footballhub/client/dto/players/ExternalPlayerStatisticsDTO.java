package org.iliuta.footballhub.client.dto.players;

import org.iliuta.footballhub.client.dto.statistics.ExternalLeagueDTO;
import org.iliuta.footballhub.client.dto.statistics.ExternalTeamDTO;

public record ExternalPlayerStatisticsDTO(
        ExternalTeamDTO team,
        ExternalLeagueDTO league,
        ExternalGamesDTO games,
        ExternalSubstitutesDTO substitutes,
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

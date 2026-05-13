package org.iliuta.footballhub.players.statistics.dto;

import org.iliuta.footballhub.teams.statistics.dto.LeagueDTO;
import org.iliuta.footballhub.teams.statistics.dto.TeamDTO;

public record PlayerStatisticsDTO(
        TeamDTO team,
        LeagueDTO league,
        GamesDTO games,
        SubstitutesDTO substitutes,
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

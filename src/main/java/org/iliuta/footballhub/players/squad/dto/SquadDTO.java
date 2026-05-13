package org.iliuta.footballhub.players.squad.dto;

import org.iliuta.footballhub.teams.statistics.dto.TeamDTO;

import java.util.List;

public record SquadDTO(
        TeamDTO team,
        List<SquadPlayerDTO> players
) {
}

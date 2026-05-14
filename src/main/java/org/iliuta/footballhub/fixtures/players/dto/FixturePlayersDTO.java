package org.iliuta.footballhub.fixtures.players.dto;

import org.iliuta.footballhub.teams.statistics.dto.TeamDTO;

import java.util.List;

public record FixturePlayersDTO(
        TeamDTO team,
        List<PlayerDTO> players
) {
}

package org.iliuta.footballhub.fixtures.players.dto;

import java.util.List;

public record FixturePlayersResponseDTO(
        List<FixturePlayersDTO> response
) {
}

package org.iliuta.footballhub.client.dto.fixtures.players;

import java.util.List;

public record ExternalFixturesPlayersResponseDTO(
        List<ExternalFixturePlayersDTO> response
) {
}

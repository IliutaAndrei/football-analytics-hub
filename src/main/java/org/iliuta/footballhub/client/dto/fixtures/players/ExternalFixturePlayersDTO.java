package org.iliuta.footballhub.client.dto.fixtures.players;

import org.iliuta.footballhub.client.dto.statistics.ExternalTeamDTO;

import java.util.List;

public record ExternalFixturePlayersDTO(
        ExternalTeamDTO team,
        List<ExternalPlayerDTO> players
) {
}

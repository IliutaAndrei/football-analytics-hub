package org.iliuta.footballhub.standings.dto;

import java.util.List;

public record StandingsResponseDTO(
        Integer leagueId,
        String leagueName,
        String country,
        String logo,
        String flag,
        Integer season,
        List<StandingDTO> standings

) {
}

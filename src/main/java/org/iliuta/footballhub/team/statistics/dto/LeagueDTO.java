package org.iliuta.footballhub.team.statistics.dto;

public record LeagueDTO(
        Integer id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season
) {
}

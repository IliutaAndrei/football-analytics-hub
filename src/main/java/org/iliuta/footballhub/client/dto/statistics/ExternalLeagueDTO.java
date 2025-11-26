package org.iliuta.footballhub.client.dto.statistics;

public record ExternalLeagueDTO(
        Integer id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season
) { }

package org.iliuta.footballhub.client.dto.league;

public record ExternalLeagueInfoDTO(
        Integer id,
        String name,
        String type,
        String logo
) { }

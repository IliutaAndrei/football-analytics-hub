package org.iliuta.footballhub.client.dto.leagues;

public record ExternalLeagueInfoDTO(
        Integer id,
        String name,
        String type,
        String logo
) { }

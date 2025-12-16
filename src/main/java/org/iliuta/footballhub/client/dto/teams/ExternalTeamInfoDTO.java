package org.iliuta.footballhub.client.dto.teams;

public record ExternalTeamInfoDTO(
        Integer id,
        String name,
        String code,
        String country,
        Integer founded,
        Boolean national,
        String logo
) { }

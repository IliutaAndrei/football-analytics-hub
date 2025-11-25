package org.iliuta.footballhub.client.dto.team;

public record ExternalTeamInfoDTO(
        Integer id,
        String name,
        String code,
        String country,
        Integer founded,
        Boolean national,
        String logo
) { }

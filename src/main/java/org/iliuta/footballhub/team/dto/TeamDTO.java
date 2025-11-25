package org.iliuta.footballhub.team.dto;

public record TeamDTO(
        Integer id,
        Integer externalId,
        String name,
        String code,
        String country,
        Integer founded,
        Boolean national,
        String logo,

        String leagueName,
        Integer leagueId,

        Integer seasonYear,

        VenueDTO venue
) { }

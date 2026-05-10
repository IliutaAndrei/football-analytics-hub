package org.iliuta.footballhub.standings.dto;

public record TeamDTO(
        Integer id,
        Integer externalId,
        String name,
        String logo
) {
}

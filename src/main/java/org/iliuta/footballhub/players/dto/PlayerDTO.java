package org.iliuta.footballhub.players.dto;

public record PlayerDTO(
        Integer id,
        Integer externalId,
        String name,
        String firstName,
        String lastName,
        String birthDate,
        String nationality,
        String height,
        String weight,
        String position,
        String photo
) {
}

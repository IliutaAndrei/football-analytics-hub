package org.iliuta.footballhub.client.dto.players;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalPlayerInfoDTO(
        Integer id,
        String name,
        @JsonProperty("firstname")
        String firstName,
        @JsonProperty("lastname")
        String lastName,

        @JsonProperty("birth")
        ExternalPlayerBirthDTO birthDate,
        String nationality,
        String height,
        String weight,
        Boolean injured,
        String photo
) {
}

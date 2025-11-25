package org.iliuta.footballhub.client.dto.team;

public record ExternalVenueDTO(
        Integer id,
        String name,
        String address,
        String city,
        Integer capacity,
        String surface,
        String image
) { }

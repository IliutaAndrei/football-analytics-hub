package org.iliuta.footballhub.client.dto.countries;


public record ExternalCountryDTO(
        Integer id,
        String name,
        String code,
        String flag
) {
}

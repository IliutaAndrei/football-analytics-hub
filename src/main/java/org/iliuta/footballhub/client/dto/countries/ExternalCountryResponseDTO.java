package org.iliuta.footballhub.client.dto.countries;

import java.util.List;

public record ExternalCountryResponseDTO(
        List<ExternalCountryDTO> response
) {
}

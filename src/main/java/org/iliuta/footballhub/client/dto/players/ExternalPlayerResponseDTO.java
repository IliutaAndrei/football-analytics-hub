package org.iliuta.footballhub.client.dto.players;

import java.util.List;

public record ExternalPlayerResponseDTO(
        ExternalPagingDTO paging,
        List<ExternalPlayerDTO> response
) {
}


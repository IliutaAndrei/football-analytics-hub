package org.iliuta.footballhub.client.dto.teams;

import java.util.List;

public record ExternalTeamResponseDTO(
        List<ExternalTeamDTO> response
) { }

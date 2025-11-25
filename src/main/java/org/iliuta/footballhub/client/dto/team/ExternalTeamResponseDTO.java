package org.iliuta.footballhub.client.dto.team;

import java.util.List;

public record ExternalTeamResponseDTO(
        List<ExternalTeamDTO> response
) { }

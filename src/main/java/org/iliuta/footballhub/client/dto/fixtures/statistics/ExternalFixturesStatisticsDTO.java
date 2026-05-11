package org.iliuta.footballhub.client.dto.fixtures.statistics;
import org.iliuta.footballhub.client.dto.statistics.ExternalTeamDTO;

import java.util.List;

public record ExternalFixturesStatisticsDTO(
        ExternalTeamDTO team,
        List<ExternalFixturesStatDTO> statistics

) {
}

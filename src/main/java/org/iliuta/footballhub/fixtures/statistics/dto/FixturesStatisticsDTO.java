package org.iliuta.footballhub.fixtures.statistics.dto;
import org.iliuta.footballhub.teams.statistics.dto.TeamDTO;

import java.util.List;

public record FixturesStatisticsDTO(
        TeamDTO team,
        List<FixturesStatDTO> statistics

) {
}

package org.iliuta.footballhub.client.dto.standings;

public record ExternalTeamMatchStatsDTO(
        Integer played,
        Integer win,
        Integer draw,
        Integer lose,
        ExternalTeamGoalsDTO goals
) {
}

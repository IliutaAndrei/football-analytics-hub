package org.iliuta.footballhub.teams.statistics.dto;

import java.util.List;

public record TeamStatisticsDTO(
        LeagueDTO league,
        TeamDTO team,
        String form,
        FixtureDTO fixture,
        GoalDTO goal,
        BiggestDTO biggest,
        CleanSheetDTO cleanSheet,
        FailedToScoreDTO failedToScore,
        PenaltyDTO penalty,
        List<LineUpDTO> lineUps
) {
}

package org.iliuta.footballhub.client.dto.statistics;

import java.util.List;

public record ExternalTeamStatisticsResponseDTO(
        ExternalLeagueDTO league,
        ExternalTeamDTO team,
        String form,
        ExternalFixtureDTO fixtures,
        ExternalGoalDTO goals,
        ExternalBiggestDTO biggest,
        ExternalCleanSheetDTO cleanSheet,
        ExternalFailedToScoreDTO failedToScore,
        ExternalPenaltyDTO penalty,
        List<ExternalLineUpDTO> lineUps,
        ExternalCardDTO cards
) { }

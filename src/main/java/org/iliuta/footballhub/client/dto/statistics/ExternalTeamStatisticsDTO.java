package org.iliuta.footballhub.client.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExternalTeamStatisticsDTO(
        ExternalLeagueDTO league,
        ExternalTeamDTO team,
        String form,
        ExternalFixtureDTO fixtures,
        ExternalGoalDTO goals,
        ExternalBiggestDTO biggest,
        @JsonProperty("clean_sheet")
        ExternalCleanSheetDTO cleanSheet,
        @JsonProperty("failed_to_score")
        ExternalFailedToScoreDTO failedToScore,
        ExternalPenaltyDTO penalty,
        @JsonProperty("lineups")
        List<ExternalLineUpDTO> lineUps,
        ExternalCardDTO cards
) { }

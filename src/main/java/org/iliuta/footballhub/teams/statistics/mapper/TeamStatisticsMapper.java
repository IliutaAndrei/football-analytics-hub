package org.iliuta.footballhub.teams.statistics.mapper;

import org.iliuta.footballhub.client.dto.statistics.*;
import org.iliuta.footballhub.teams.statistics.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamStatisticsMapper {


    @Mapping(source = "fixtures", target = "fixture")
    @Mapping(source = "goals", target = "goal")
    TeamStatisticsDTO toTeamStatisticsDTO(ExternalTeamStatisticsDTO responseDTO);

    FixtureDTO toFixtureDTO(ExternalFixtureDTO external);
    LeagueDTO toLeagueDTO(ExternalLeagueDTO external);
    TeamDTO toTeamDTO(ExternalTeamDTO external);
    GoalDTO toGoalDTO(ExternalGoalDTO external);
    BiggestDTO toBiggestDTO(ExternalBiggestDTO external);
    CleanSheetDTO toCleanSheetDTO(ExternalCleanSheetDTO external);
    FailedToScoreDTO toFailedToScoreDTO(ExternalFailedToScoreDTO external);
    PenaltyDTO toPenaltyDTO(ExternalPenaltyDTO external);

    LineUpDTO toLineUpDTO(ExternalLineUpDTO external);
}

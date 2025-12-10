package org.iliuta.footballhub.standings.mapper;

import org.iliuta.footballhub.client.dto.standings.*;
import org.iliuta.footballhub.standings.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandingsMapper {

    @Mapping(source = "all", target = "summary")
    @Mapping(source = "form", target = "form")
    StandingDTO toStandingDTO(ExternalStandingsSummaryDTO external);

    TeamDTO toTeamDTO(ExternalTeamSummaryDTO external);

    @Mapping(source = "against", target = "againstGoals")
    GoalsDTO toGoalsDTO(ExternalTeamGoalsDTO external);

    MatchesSummaryDTO toMatchesSummaryDTO(ExternalTeamMatchStatsDTO external);
}

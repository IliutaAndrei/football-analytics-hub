package org.iliuta.footballhub.fixtures.mapper;

import org.iliuta.footballhub.client.dto.fixtures.*;
import org.iliuta.footballhub.fixtures.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FixturesMapper {

    FixtureResponseDTO toFixtureResponseDTO(ExternalFixtureResponseDTO external);
    FixtureTeamDTO toFixtureTeamDTO(ExternalFixtureTeamDTO external);

    FixtureScoreDTO toFixtureScoreDTO(ExternalScoreDTO external);
    ScoreSummaryDTO toScoreSummaryDTO(ExternalScoreSummaryDTO external);
}

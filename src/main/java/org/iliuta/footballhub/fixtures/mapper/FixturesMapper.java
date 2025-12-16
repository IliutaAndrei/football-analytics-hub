package org.iliuta.footballhub.fixtures.mapper;

import org.iliuta.footballhub.client.dto.fixtures.ExternalFixtureTeamDTO;
import org.iliuta.footballhub.client.dto.fixtures.ExternalScoreDTO;
import org.iliuta.footballhub.client.dto.fixtures.ExternalScoreSummaryDTO;
import org.iliuta.footballhub.fixtures.dto.FixtureScoreDTO;
import org.iliuta.footballhub.fixtures.dto.FixtureTeamDTO;
import org.iliuta.footballhub.fixtures.dto.ScoreSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FixturesMapper {

    FixtureTeamDTO toFixtureTeamDTO(ExternalFixtureTeamDTO external);
    FixtureScoreDTO toFixtureScoreDTO(ExternalScoreDTO external);
    ScoreSummaryDTO toScoreSummaryDTO(ExternalScoreSummaryDTO external);
}

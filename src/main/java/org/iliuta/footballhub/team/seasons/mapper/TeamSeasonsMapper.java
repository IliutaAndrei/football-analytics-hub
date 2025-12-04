package org.iliuta.footballhub.team.seasons.mapper;

import org.iliuta.footballhub.client.dto.seasons.ExternalTeamSeasonsResponseDTO;
import org.iliuta.footballhub.team.seasons.dto.TeamSeasonsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamSeasonsMapper {

    @Mapping(target = "seasons", source = "response")
    TeamSeasonsDTO toTeamSeasonsDTO(ExternalTeamSeasonsResponseDTO external);
}

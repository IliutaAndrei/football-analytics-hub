package org.iliuta.footballhub.teams.mapper;

import org.iliuta.footballhub.client.dto.teams.ExternalTeamInfoDTO;
import org.iliuta.footballhub.client.dto.teams.ExternalVenueDTO;
import org.iliuta.footballhub.teams.TeamEntity;
import org.iliuta.footballhub.teams.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExternalTeamMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "season", ignore = true)
    @Mapping(target = "venue", ignore = true)
    @Mapping(target = "league", ignore = true)
    TeamEntity toTeamEntity(ExternalTeamInfoDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "season", ignore = true)
    @Mapping(target = "venue", ignore = true)
    @Mapping(target = "league", ignore = true)
    void updateTeamEntity(@MappingTarget TeamEntity entity, ExternalTeamInfoDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "externalId", source = "id")
    VenueEntity toVenueEntity(ExternalVenueDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateVenueEntity(@MappingTarget VenueEntity entity, ExternalVenueDTO dto);
}

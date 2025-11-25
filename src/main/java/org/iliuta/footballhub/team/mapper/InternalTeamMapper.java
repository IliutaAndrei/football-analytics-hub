package org.iliuta.footballhub.team.mapper;

import org.iliuta.footballhub.team.TeamEntity;
import org.iliuta.footballhub.team.VenueEntity;
import org.iliuta.footballhub.team.dto.TeamDTO;
import org.iliuta.footballhub.team.dto.VenueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternalTeamMapper {

    @Mapping(target = "leagueName", source = "league.name")
    @Mapping(target = "leagueId", source = "league.externalId")
    @Mapping(target = "seasonYear", source = "season.year")
    TeamDTO toTeamDTO(TeamEntity entity);
    List<TeamDTO> toTeamDTOS(List<TeamEntity> entities);

    VenueDTO toVenueDTO(VenueEntity entity);
}

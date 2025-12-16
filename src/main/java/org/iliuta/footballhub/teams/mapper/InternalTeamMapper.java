package org.iliuta.footballhub.teams.mapper;

import org.iliuta.footballhub.teams.TeamEntity;
import org.iliuta.footballhub.teams.VenueEntity;
import org.iliuta.footballhub.teams.dto.TeamDTO;
import org.iliuta.footballhub.teams.dto.VenueDTO;
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

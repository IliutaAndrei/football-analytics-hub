package org.iliuta.footballhub.players.squad.mapper;

import org.iliuta.footballhub.players.PlayerEntity;
import org.iliuta.footballhub.players.squad.dto.SquadPlayerDTO;
import org.iliuta.footballhub.teams.TeamEntity;
import org.iliuta.footballhub.teams.statistics.dto.TeamDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternalSquadMapper {

    SquadPlayerDTO toDTO(PlayerEntity entity);
    List<SquadPlayerDTO> toDTOs(List<PlayerEntity> entities);
    TeamDTO toTeamDTO(TeamEntity entity);
}

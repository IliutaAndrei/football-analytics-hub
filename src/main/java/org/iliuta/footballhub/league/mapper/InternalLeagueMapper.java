package org.iliuta.footballhub.league.mapper;

import org.iliuta.footballhub.league.LeagueEntity;
import org.iliuta.footballhub.league.SeasonEntity;
import org.iliuta.footballhub.league.dto.LeagueDTO;
import org.iliuta.footballhub.league.dto.SeasonDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternalLeagueMapper{

    LeagueDTO toLeagueDTO(LeagueEntity entity);
    List<LeagueDTO> toLeagueDTOs(List<LeagueEntity> entities);

    SeasonDTO toSeasonDTO(SeasonEntity entity);
    List<SeasonDTO> toSeasonDTOs(List<SeasonEntity> entities);
}

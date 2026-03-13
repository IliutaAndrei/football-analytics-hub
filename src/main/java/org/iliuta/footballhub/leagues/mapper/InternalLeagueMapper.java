package org.iliuta.footballhub.leagues.mapper;

import org.iliuta.footballhub.leagues.LeagueEntity;
import org.iliuta.footballhub.leagues.SeasonEntity;
import org.iliuta.footballhub.leagues.dto.LeagueDTO;
import org.iliuta.footballhub.leagues.dto.SeasonDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternalLeagueMapper{

    LeagueDTO toLeagueDTO(LeagueEntity entity);
    List<LeagueDTO> toLeagueDTOs(List<LeagueEntity> entities);

    SeasonDTO toSeasonDTO(SeasonEntity entity);
    List<SeasonDTO> toSeasonDTOs(List<SeasonEntity> entities);
}

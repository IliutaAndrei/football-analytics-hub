package org.iliuta.footballhub.league.mapper;

import org.iliuta.footballhub.client.dto.league.ExternalCountryDTO;
import org.iliuta.footballhub.client.dto.league.ExternalLeagueInfoDTO;
import org.iliuta.footballhub.client.dto.league.ExternalSeasonDTO;
import org.iliuta.footballhub.country.CountryEntity;
import org.iliuta.footballhub.league.LeagueEntity;
import org.iliuta.footballhub.league.SeasonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExternalLeagueMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "country", ignore = true)
    LeagueEntity toLeagueEntity(ExternalLeagueInfoDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "country", ignore = true)
    void updateLeagueEntity(@MappingTarget LeagueEntity entity, ExternalLeagueInfoDTO dto);

    @Mapping(target = "id", ignore = true)
    CountryEntity toCountryEntity(ExternalCountryDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateCountryEntity(@MappingTarget CountryEntity entity, ExternalCountryDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startDate", source = "start")
    @Mapping(target = "endDate", source = "end")
    @Mapping(target = "league", ignore = true)
    SeasonEntity toSeasonEntity(ExternalSeasonDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startDate", source = "start")
    @Mapping(target = "endDate", source = "end")
    @Mapping(target = "league", ignore = true)
    void updateSeasonEntity(@MappingTarget SeasonEntity entity, ExternalSeasonDTO dto);
}

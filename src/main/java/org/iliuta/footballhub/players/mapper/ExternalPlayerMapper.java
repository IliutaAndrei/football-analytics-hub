package org.iliuta.footballhub.players.mapper;

import org.iliuta.footballhub.client.dto.players.ExternalPlayerInfoDTO;
import org.iliuta.footballhub.players.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExternalPlayerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "birthDate", source = "birthDate.date")
    PlayerEntity toEntity(ExternalPlayerInfoDTO external);

}

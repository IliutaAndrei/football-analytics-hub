package org.iliuta.footballhub.players.mapper;

import org.iliuta.footballhub.players.PlayerEntity;
import org.iliuta.footballhub.players.dto.PlayerDTO;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface InternalPlayerMapper {

    PlayerDTO toDTO(PlayerEntity entity);

    default String mapLocalDate(LocalDate date) {
        return date != null ? date.toString() : null;
    }
}

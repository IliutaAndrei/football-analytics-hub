package org.iliuta.footballhub.fixtures.players.mapper;

import org.iliuta.footballhub.client.dto.fixtures.players.ExternalFixturesPlayersResponseDTO;
import org.iliuta.footballhub.fixtures.players.dto.FixturePlayersResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FixturePlayersMapper {

    FixturePlayersResponseDTO map(ExternalFixturesPlayersResponseDTO dto);
}

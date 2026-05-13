package org.iliuta.footballhub.players.statistics.mapper;

import org.iliuta.footballhub.client.dto.players.ExternalPlayerStatisticsDTO;
import org.iliuta.footballhub.players.statistics.dto.PlayerStatisticsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerStatisticsMapper {

    PlayerStatisticsDTO map(ExternalPlayerStatisticsDTO dto);
}

package org.iliuta.footballhub.fixtures.statistics.mapper;

import org.iliuta.footballhub.client.dto.fixtures.statistics.ExternalFixturesStatisticsDTO;
import org.iliuta.footballhub.client.dto.fixtures.statistics.ExternalFixturesStatisticsResponseDTO;
import org.iliuta.footballhub.fixtures.statistics.dto.FixturesStatisticsDTO;
import org.iliuta.footballhub.fixtures.statistics.dto.FixturesStatisticsResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FixturesStatisticsMapper {

    FixturesStatisticsResponseDTO map(ExternalFixturesStatisticsResponseDTO external);
    FixturesStatisticsDTO map(ExternalFixturesStatisticsDTO external);
}

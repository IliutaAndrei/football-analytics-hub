package org.iliuta.footballhub.fixtures.statistics.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.fixtures.statistics.dto.FixturesStatisticsResponseDTO;
import org.iliuta.footballhub.fixtures.statistics.mapper.FixturesStatisticsMapper;
import org.springframework.stereotype.Service;

@Service
public class FixturesStatisticsService {

    private final FixturesStatisticsMapper mapper;
    private final FootballApiClient footballApiClient;


    public FixturesStatisticsService(FixturesStatisticsMapper mapper, FootballApiClient footballApiClient) {
        this.mapper = mapper;
        this.footballApiClient = footballApiClient;
    }

    public FixturesStatisticsResponseDTO getFixturesStatisticsByFixtureId(Integer fixtureId) {
        var response = footballApiClient
                .getFixturesStatisticsByFixtureId(fixtureId);

        return mapper.map(response);
    }
}

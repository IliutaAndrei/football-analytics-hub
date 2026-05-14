package org.iliuta.footballhub.fixtures.players.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.fixtures.players.dto.FixturePlayersResponseDTO;
import org.iliuta.footballhub.fixtures.players.mapper.FixturePlayersMapper;
import org.springframework.stereotype.Service;

@Service
public class FixturePlayersService {

    private final FootballApiClient footballApiClient;
    private final FixturePlayersMapper fixturePlayersMapper;


    public FixturePlayersService(FootballApiClient footballApiClient,
                                 FixturePlayersMapper fixturePlayersMapper
    ) {
        this.footballApiClient = footballApiClient;
        this.fixturePlayersMapper = fixturePlayersMapper;
    }

    public FixturePlayersResponseDTO getFixturePlayersByFixtureId(Integer fixtureId) {

        var response = footballApiClient.getPlayersByFixtureId(fixtureId);

        return fixturePlayersMapper.map(response);
    }

}

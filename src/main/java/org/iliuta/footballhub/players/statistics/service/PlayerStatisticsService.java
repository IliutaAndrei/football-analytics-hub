package org.iliuta.footballhub.players.statistics.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.players.PlayerRepository;
import org.iliuta.footballhub.players.statistics.dto.PlayerStatisticsDTO;
import org.iliuta.footballhub.players.statistics.mapper.PlayerStatisticsMapper;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatisticsService {

    private final PlayerRepository playerRepository;
    private final PlayerStatisticsMapper playerStatisticsMapper;
    private final FootballApiClient footballApiClient;

    public PlayerStatisticsService(PlayerRepository playerRepository, PlayerStatisticsMapper playerStatisticsMapper, FootballApiClient footballApiClient) {
        this.playerRepository = playerRepository;
        this.playerStatisticsMapper = playerStatisticsMapper;
        this.footballApiClient = footballApiClient;
    }

    public PlayerStatisticsDTO getPlayerStatisticsByPlayerId(Integer playerId) {

        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("No player found with id: " + playerId));

        var response = footballApiClient
                .getPlayerStatistics(player.getExternalId(),
                        player.getTeam().getSeason().getYear(),
                        player.getTeam().getLeague().getExternalId()
                );

        var playerData = response.response().getFirst();
        var teamExternalId = player.getTeam().getExternalId();
        var leagueExternalId = player.getTeam().getLeague().getExternalId();

        var teamStats = playerData.statistics().stream()
                .filter(s -> s.team().id().equals(teamExternalId)
                             && s.league().id().equals(leagueExternalId))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("No statistics found")
                );

        return playerStatisticsMapper.map(teamStats);
    }
}

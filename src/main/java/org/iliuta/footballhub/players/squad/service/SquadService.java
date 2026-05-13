package org.iliuta.footballhub.players.squad.service;

import org.iliuta.footballhub.client.FootballApiClient;
import org.iliuta.footballhub.client.dto.players.ExternalPlayerDTO;
import org.iliuta.footballhub.client.dto.players.ExternalPlayerResponseDTO;
import org.iliuta.footballhub.players.PlayerRepository;
import org.iliuta.footballhub.players.mapper.ExternalPlayerMapper;
import org.iliuta.footballhub.players.squad.dto.SquadDTO;
import org.iliuta.footballhub.players.squad.mapper.InternalSquadMapper;
import org.iliuta.footballhub.teams.TeamEntity;
import org.iliuta.footballhub.teams.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class SquadService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final FootballApiClient footballApiClient;
    private final InternalSquadMapper internalSquadMapper;
    private final ExternalPlayerMapper externalPlayerMapper;


    public SquadService(PlayerRepository playerRepository,
                        TeamRepository teamRepository,
                        FootballApiClient footballApiClient,
                        InternalSquadMapper internalSquadMapper, ExternalPlayerMapper externalPlayerMapper
    ) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.footballApiClient = footballApiClient;
        this.internalSquadMapper = internalSquadMapper;
        this.externalPlayerMapper = externalPlayerMapper;
    }

    public SquadDTO getPlayersByTeamId(Integer teamId) {

        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("No team found with id: " + teamId));

        var seasonYear = team.getSeason().getYear();

        var firstPage = footballApiClient
                .getPlayersByTeamIdSeasonYearAndPage(team.getExternalId(), seasonYear, 1);

        if (firstPage == null || firstPage.response() == null || firstPage.response().isEmpty()) {
            throw new RuntimeException("No players found");
        }

        int totalPages = firstPage.paging().total();

        savePlayersFromResponse(firstPage, team);

        for (int page = 2; page <= totalPages; page++) {
            var nextPage = footballApiClient
                    .getPlayersByTeamIdSeasonYearAndPage(team.getExternalId(), seasonYear,page);
            savePlayersFromResponse(nextPage, team);
        }


        var savedPlayers = playerRepository.findByTeamId(team.getId());
        var squadPlayerDTOS = internalSquadMapper.toDTOs(savedPlayers);

        return new SquadDTO(
                internalSquadMapper.toTeamDTO(team),
                squadPlayerDTOS
        );
    }

    private void savePlayersFromResponse(ExternalPlayerResponseDTO response, TeamEntity team) {
        for (ExternalPlayerDTO playerDTO : response.response()) {
            var playerInfo = playerDTO.player();

            if (playerRepository.findByExternalIdAndTeamId(playerInfo.id(), team.getId()).isEmpty()) {
                var entity = externalPlayerMapper.toEntity(playerInfo);
                entity.setTeam(team);

                var stats = playerDTO.statistics();
                if (stats != null && !stats.isEmpty() && stats.getFirst().games() != null) {
                    entity.setPosition(stats.getFirst().games().position());
                }
                playerRepository.save(entity);
            }
        }
    }
}

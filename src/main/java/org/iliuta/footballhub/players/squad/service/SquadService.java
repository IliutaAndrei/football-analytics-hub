package org.iliuta.footballhub.players.squad.service;

import lombok.extern.slf4j.Slf4j;
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

import java.util.Collections;

@Service
@Slf4j
public class SquadService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final FootballApiClient footballApiClient;
    private final InternalSquadMapper internalSquadMapper;
    private final ExternalPlayerMapper externalPlayerMapper;

    public SquadService(PlayerRepository playerRepository,
                        TeamRepository teamRepository,
                        FootballApiClient footballApiClient,
                        InternalSquadMapper internalSquadMapper,
                        ExternalPlayerMapper externalPlayerMapper) {
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

        log.info("Fetching squad for team {} (external id: {}) season {}",
                team.getName(), team.getExternalId(), seasonYear);

        var firstPage = footballApiClient
                .getPlayersByTeamIdSeasonYearAndPage(team.getExternalId(), seasonYear, 1);

        if (firstPage == null || firstPage.response() == null || firstPage.response().isEmpty()) {
            log.warn("No players available for team {} season {} (API returned empty response)",
                    team.getName(), seasonYear);

            return new SquadDTO(
                    internalSquadMapper.toTeamDTO(team),
                    Collections.emptyList()
            );
        }

        int totalPages = firstPage.paging().total();
        log.info("Fetching {} pages of players for team {}", totalPages, team.getName());

        savePlayersFromResponse(firstPage, team);

        for (int page = 2; page <= totalPages; page++) {
            var nextPage = footballApiClient
                    .getPlayersByTeamIdSeasonYearAndPage(team.getExternalId(), seasonYear, page);
            savePlayersFromResponse(nextPage, team);
        }

        var savedPlayers = playerRepository.findByTeamId(team.getId());
        var squadPlayerDTOS = internalSquadMapper.toDTOs(savedPlayers);

        log.info("Successfully fetched {} players for team {}", squadPlayerDTOS.size(), team.getName());

        return new SquadDTO(
                internalSquadMapper.toTeamDTO(team),
                squadPlayerDTOS
        );
    }

    private void savePlayersFromResponse(ExternalPlayerResponseDTO response, TeamEntity team) {
        if (response == null || response.response() == null) {
            return;
        }

        for (ExternalPlayerDTO playerDTO : response.response()) {
            var playerInfo = playerDTO.player();

            if (playerRepository.findByExternalIdAndTeamId(playerInfo.id(), team.getId()).isEmpty()) {
                try {
                    var entity = externalPlayerMapper.toEntity(playerInfo);
                    entity.setTeam(team);

                    var stats = playerDTO.statistics();
                    if (stats != null && !stats.isEmpty() && stats.getFirst().games() != null) {
                        entity.setPosition(stats.getFirst().games().position());
                    }

                    playerRepository.save(entity);
                } catch (Exception e) {
                    log.error("Failed to save player {} for team {}: {}",
                            playerInfo.name(), team.getName(), e.getMessage());
                }
            }
        }
    }
}
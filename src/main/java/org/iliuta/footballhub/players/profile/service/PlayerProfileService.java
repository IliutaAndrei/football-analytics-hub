package org.iliuta.footballhub.players.profile.service;

import org.iliuta.footballhub.players.PlayerRepository;
import org.iliuta.footballhub.players.dto.PlayerDTO;
import org.iliuta.footballhub.players.mapper.InternalPlayerMapper;
import org.springframework.stereotype.Service;

@Service
public class PlayerProfileService {

    private final InternalPlayerMapper internalPlayerMapper;
    private final PlayerRepository playerRepository;


    public PlayerProfileService(InternalPlayerMapper internalPlayerMapper, PlayerRepository playerRepository) {
        this.internalPlayerMapper = internalPlayerMapper;
        this.playerRepository = playerRepository;
    }

    public PlayerDTO getPlayerProfileByPlayerId(Integer playerId) {
        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("No player found with id: " + playerId));

        return internalPlayerMapper.toDTO(player);
    }
}

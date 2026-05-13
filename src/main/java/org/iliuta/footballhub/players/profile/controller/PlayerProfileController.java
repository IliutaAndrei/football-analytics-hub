package org.iliuta.footballhub.players.profile.controller;

import org.iliuta.footballhub.players.dto.PlayerDTO;
import org.iliuta.footballhub.players.profile.service.PlayerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;

    public PlayerProfileController(PlayerProfileService playerProfileService) {
        this.playerProfileService = playerProfileService;
    }

    @GetMapping("players/{playerId}/profile")
    public ResponseEntity<PlayerDTO> getPlayerProfileByPlayerId(@PathVariable Integer playerId) {
        var data = playerProfileService.getPlayerProfileByPlayerId(playerId);

        return ResponseEntity.ok(data);
    }
}

package org.iliuta.footballhub.players.statistics.controller;

import org.iliuta.footballhub.players.statistics.dto.PlayerStatisticsDTO;
import org.iliuta.footballhub.players.statistics.service.PlayerStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class PlayerStatisticsController {

    private final PlayerStatisticsService service;


    public PlayerStatisticsController(PlayerStatisticsService service) {
        this.service = service;
    }

    @GetMapping("players/{playerId}/statistics")
    public ResponseEntity<PlayerStatisticsDTO> getPlayerStatisticsByPlayerId(@PathVariable Integer playerId) {
        var data = service.getPlayerStatisticsByPlayerId(playerId);

        return ResponseEntity.ok(data);
    }
}

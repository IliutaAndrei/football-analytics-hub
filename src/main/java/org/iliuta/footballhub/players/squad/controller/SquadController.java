package org.iliuta.footballhub.players.squad.controller;

import org.iliuta.footballhub.players.squad.dto.SquadDTO;
import org.iliuta.footballhub.players.squad.service.SquadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class SquadController {

    private final SquadService squadService;

    public SquadController(SquadService squadService) {
        this.squadService = squadService;
    }

    @GetMapping("teams/{teamId}/squad")
    public ResponseEntity<SquadDTO> getSquadPlayersByTeamId(@PathVariable Integer teamId) {

        var data = squadService.getPlayersByTeamId(teamId);

        return ResponseEntity.ok(data);
    }
}

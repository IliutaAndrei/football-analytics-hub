package org.iliuta.footballhub.fixtures.players.controller;

import org.iliuta.footballhub.fixtures.players.dto.FixturePlayersResponseDTO;
import org.iliuta.footballhub.fixtures.players.service.FixturePlayersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FixturePlayersController {

    private final FixturePlayersService service;


    public FixturePlayersController(FixturePlayersService service) {
        this.service = service;
    }

    @GetMapping("/fixtures/{fixtureId}/players")
    public ResponseEntity<FixturePlayersResponseDTO> getFixturePlayersByFixtureId(
            @PathVariable Integer fixtureId) {

        var data = service.getFixturePlayersByFixtureId(fixtureId);

        return ResponseEntity.ok(data);
    }
}

package org.iliuta.footballhub.teams.seasons.controller;

import org.iliuta.footballhub.teams.seasons.dto.TeamSeasonsDTO;
import org.iliuta.footballhub.teams.seasons.service.TeamSeasonsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamSeasonsController {

    private final TeamSeasonsService teamSeasonsService;

    public TeamSeasonsController(TeamSeasonsService teamSeasonsService) {
        this.teamSeasonsService = teamSeasonsService;
    }

    @GetMapping("teams/{teamId}/seasons")
    public ResponseEntity<TeamSeasonsDTO> getSeasonsByTeamId(
            @PathVariable  Integer teamId) {
        var response = teamSeasonsService.getSeasonsByTeamId(teamId);

        return ResponseEntity.ok(response);
    }
}

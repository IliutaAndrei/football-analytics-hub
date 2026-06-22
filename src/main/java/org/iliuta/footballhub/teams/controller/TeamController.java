package org.iliuta.footballhub.teams.controller;

import lombok.RequiredArgsConstructor;
import org.iliuta.footballhub.teams.dto.TeamDTO;
import org.iliuta.footballhub.teams.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/leagues")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{leagueId}/seasons/{seasonYear}/teams/by-external-id/{externalTeamId}")
    public ResponseEntity<TeamDTO> getTeamByExternalId(
            @PathVariable Integer leagueId,
            @PathVariable Integer seasonYear,
            @PathVariable Integer externalTeamId) {

        var team = teamService.getTeamByExternalIdInContext(externalTeamId, leagueId, seasonYear);
        return ResponseEntity.ok(team);
    }


    @GetMapping("/{leagueId}/seasons/{seasonYear}/teams/{teamId}")
    public ResponseEntity<TeamDTO> getTeamById(
            @PathVariable Integer leagueId,
            @PathVariable Integer seasonYear,
            @PathVariable Integer teamId) {
        var team = teamService.getTeamByIdInContext(teamId, leagueId, seasonYear);

        return ResponseEntity.ok(team);
    }

    @GetMapping("/{leagueId}/seasons/{seasonYear}/teams")
    public ResponseEntity<List<TeamDTO>> getTeams(
            @PathVariable("leagueId") Integer leagueId, @PathVariable("seasonYear") Integer seasonYear) {

       var teams = teamService.getTeamsByLeagueIdAndSeasonYear(leagueId, seasonYear);

        return ResponseEntity.ok(teams);
    }
}

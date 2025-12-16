package org.iliuta.footballhub.teams.controller;

import lombok.RequiredArgsConstructor;
import org.iliuta.footballhub.teams.TeamRepository;
import org.iliuta.footballhub.teams.dto.TeamDTO;
import org.iliuta.footballhub.teams.mapper.InternalTeamMapper;
import org.iliuta.footballhub.teams.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final InternalTeamMapper internalTeamMapper;

    @PostMapping("/sync/{leagueId}/{seasonYear}")
    public ResponseEntity<Void> syncTeamsByLeagueAndSeason(
            @PathVariable("leagueId") Integer leagueId, @PathVariable("seasonYear") Integer seasonYear) {
        teamService.syncTeamByLeagueAndSeason(leagueId, seasonYear);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/league/{leagueId}/season/{seasonYear}")
    public ResponseEntity<List<TeamDTO>> getTeamsByLeagueIdAndSeasonYear(
            @PathVariable("leagueId") Integer leagueId, @PathVariable("seasonYear") Integer seasonYear) {

        var teams = teamRepository.findByLeague_ExternalIdAndSeason_Year(leagueId, seasonYear);
        var dtos = internalTeamMapper.toTeamDTOS(teams);

        return ResponseEntity.ok(dtos);
    }
}

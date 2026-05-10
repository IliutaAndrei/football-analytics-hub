package org.iliuta.footballhub.teams.statistics.controller;

import lombok.RequiredArgsConstructor;
import org.iliuta.footballhub.teams.statistics.dto.TeamStatisticsDTO;
import org.iliuta.footballhub.teams.statistics.service.TeamStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/leagues")
public class TeamStatisticsController {

    private final TeamStatisticsService teamStatisticsService;

    @GetMapping("/{leagueId}/seasons/{seasonYear}/teams/{teamId}/statistics")
    public ResponseEntity<TeamStatisticsDTO> getStatistics(
            @PathVariable("leagueId") Integer leagueId,
            @PathVariable("seasonYear") Integer seasonYear,
            @PathVariable("teamId") Integer teamId
    ) {
        var response = teamStatisticsService
                .getTeamStatistics(leagueId, teamId, seasonYear);

        return ResponseEntity.ok(response);
    }
}

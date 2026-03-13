package org.iliuta.footballhub.teams.statistics.controller;

import org.iliuta.footballhub.teams.statistics.dto.TeamStatisticsDTO;
import org.iliuta.footballhub.teams.statistics.service.TeamStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamStatisticsController {

    private final TeamStatisticsService teamStatisticsService;


    public TeamStatisticsController(TeamStatisticsService teamStatisticsService) {
        this.teamStatisticsService = teamStatisticsService;
    }

    @GetMapping("leagues/{leagueId}/seasons/{seasonYear}/teams/{teamId}/statistics")
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

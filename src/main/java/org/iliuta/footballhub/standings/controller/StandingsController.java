package org.iliuta.footballhub.standings.controller;

import org.iliuta.footballhub.standings.dto.StandingsResponseDTO;
import org.iliuta.footballhub.standings.service.StandingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StandingsController {

    private final StandingsService standingsService;

    public StandingsController(StandingsService standingsService) {
        this.standingsService = standingsService;
    }

    @GetMapping("/leagues/{leagueId}/seasons/{seasonYear}/standings")
    public ResponseEntity<StandingsResponseDTO> getStandingsByLeagueIdAndSeasonYear(
            @PathVariable Integer leagueId, @PathVariable Integer seasonYear) {
        var response = standingsService
                .getStandingsByLeagueIdAndSeasonYear(leagueId, seasonYear);

        return ResponseEntity.ok(response);
    }
}

package org.iliuta.footballhub.fixtures.controller;

import lombok.RequiredArgsConstructor;
import org.iliuta.footballhub.fixtures.dto.FixtureResponseDTO;
import org.iliuta.footballhub.fixtures.service.FixtureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FixturesController {

    private final FixtureService fixtureService;

    @GetMapping("/leagues/{leagueId}/seasons/{seasonYear}/fixtures")
    public ResponseEntity<FixtureResponseDTO> getFixturesByLeagueIdAndSeasonYear(
            @PathVariable Integer leagueId,
            @PathVariable Integer seasonYear
    ) {
        var data = fixtureService.getFixturesByLeagueIdAndSeasonYear(
                leagueId, seasonYear
        );

        return ResponseEntity.ok(data);
    }

    @GetMapping("/leagues/{leagueId}/seasons/{seasonYear}/teams/{teamId}/fixtures")
    public ResponseEntity<FixtureResponseDTO> getFixturesByLeagueIdSeasonYearAndTeamId(
            @PathVariable Integer leagueId,
            @PathVariable Integer seasonYear,
            @PathVariable Integer teamId
    ) {
        var data = fixtureService.getFixturesByLeagueIdSeasonYearAndTeamId(
                leagueId, seasonYear, teamId
        );

        return ResponseEntity.ok(data);
    }
}

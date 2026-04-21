package org.iliuta.footballhub.leagues.controller;

import lombok.RequiredArgsConstructor;
import org.iliuta.footballhub.leagues.dto.LeagueDTO;
import org.iliuta.footballhub.leagues.dto.SeasonDTO;
import org.iliuta.footballhub.leagues.service.LeagueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;

    @GetMapping("/country/{code}")
    public ResponseEntity<List<LeagueDTO>> getLeaguesByCountry(@PathVariable String code) {
        List<LeagueDTO> leagues = leagueService.getLeaguesByCountryCode(code);

        return ResponseEntity.ok(leagues);
    }

    @GetMapping("/{id}/seasons")
    public ResponseEntity<List<SeasonDTO>> getSeasonsByLeagueId(@PathVariable("id") int leagueId){
        List<SeasonDTO> seasons = leagueService.getSeasonsByLeagueId(leagueId);

        return ResponseEntity.ok(seasons);
    }
}

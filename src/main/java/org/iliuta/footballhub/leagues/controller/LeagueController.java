package org.iliuta.footballhub.leagues.controller;

import lombok.RequiredArgsConstructor;
import org.iliuta.footballhub.leagues.LeagueRepository;
import org.iliuta.footballhub.leagues.SeasonRepository;
import org.iliuta.footballhub.leagues.dto.LeagueDTO;
import org.iliuta.footballhub.leagues.dto.SeasonDTO;
import org.iliuta.footballhub.leagues.mapper.InternalLeagueMapper;
import org.iliuta.footballhub.leagues.service.LeagueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final InternalLeagueMapper internalLeagueMapper;

    @PostMapping("/sync/{countryCode}")
    public ResponseEntity<Void> syncLeagues(@PathVariable String countryCode) {
        leagueService.syncLeaguesByCountry(countryCode);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/country/{code}")
    public ResponseEntity<List<LeagueDTO>> getLeaguesByCountry(@PathVariable String code) {
        var leagues = leagueRepository.findByCountry_Code(code);

        var dtos = internalLeagueMapper.toLeagueDTOs(leagues);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/seasons")
    public ResponseEntity<List<SeasonDTO>> getSeasonsByLeagueId(@PathVariable("id") int leagueId){
        var seasons = seasonRepository.findByLeague_Id(leagueId);

        var dtos = internalLeagueMapper.toSeasonDTOs(seasons);

        return ResponseEntity.ok(dtos);
    }
}

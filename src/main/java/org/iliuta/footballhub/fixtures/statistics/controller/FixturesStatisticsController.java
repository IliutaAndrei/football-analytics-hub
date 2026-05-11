package org.iliuta.footballhub.fixtures.statistics.controller;

import org.iliuta.footballhub.fixtures.statistics.dto.FixturesStatisticsResponseDTO;
import org.iliuta.footballhub.fixtures.statistics.service.FixturesStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FixturesStatisticsController {

    private final FixturesStatisticsService service;


    public FixturesStatisticsController(FixturesStatisticsService service) {
        this.service = service;
    }

    @GetMapping("/fixtures/{fixtureId}/statistics")
    public ResponseEntity<FixturesStatisticsResponseDTO> getFixturesStatisticsByFixtureId(
            @PathVariable Integer fixtureId) {
        var data = service.getFixturesStatisticsByFixtureId(fixtureId);

        return ResponseEntity.ok(data);
    }
}

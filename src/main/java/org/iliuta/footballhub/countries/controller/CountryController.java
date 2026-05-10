package org.iliuta.footballhub.countries.controller;

import org.iliuta.footballhub.countries.dto.CountryDTO;
import org.iliuta.footballhub.countries.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncAllCountries() {
        countryService.syncAllCountries();

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }
}

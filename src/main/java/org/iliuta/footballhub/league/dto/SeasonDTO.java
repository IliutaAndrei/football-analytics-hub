package org.iliuta.footballhub.league.dto;

import java.time.LocalDate;

public record SeasonDTO(
        Integer id,
        Integer year,
        LocalDate startDate,
        LocalDate endDate,
        Boolean current
) { }

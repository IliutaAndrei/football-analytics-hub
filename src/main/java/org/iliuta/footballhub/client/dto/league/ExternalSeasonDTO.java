package org.iliuta.footballhub.client.dto.league;

import java.time.LocalDate;

public record ExternalSeasonDTO(
        Integer year,
        LocalDate start,
        LocalDate end,
        Boolean current
) { }

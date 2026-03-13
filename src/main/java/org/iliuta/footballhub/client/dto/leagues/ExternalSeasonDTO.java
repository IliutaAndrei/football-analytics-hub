package org.iliuta.footballhub.client.dto.leagues;

import java.time.LocalDate;

public record ExternalSeasonDTO(
        Integer year,
        LocalDate start,
        LocalDate end,
        Boolean current
) { }

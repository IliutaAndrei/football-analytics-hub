package org.iliuta.footballhub.league;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "seasons")
public class SeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer year;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean current;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private LeagueEntity league;
}

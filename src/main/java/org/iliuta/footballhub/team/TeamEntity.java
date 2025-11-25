package org.iliuta.footballhub.team;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iliuta.footballhub.league.LeagueEntity;
import org.iliuta.footballhub.league.SeasonEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer externalId;
    private String name;
    private String code;
    private String country;
    private Boolean national;
    private Integer founded;
    private String logo;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private SeasonEntity season;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private LeagueEntity league;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "venue_id")
    private VenueEntity venue;
}

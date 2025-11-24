package org.iliuta.footballhub.league;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iliuta.footballhub.country.CountryEntity;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "leagues")
public class LeagueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer externalId;
    private String name;
    private String type;
    private String logo;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

}

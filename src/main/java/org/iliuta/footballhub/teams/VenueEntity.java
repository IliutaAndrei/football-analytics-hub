package org.iliuta.footballhub.teams;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "venues")
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer externalId;
    private String name;
    private String address;
    private String city;
    private Integer capacity;
    private String surface;
    private String image;

    @OneToOne(mappedBy = "venue",orphanRemoval = true)
    private TeamEntity team;
}

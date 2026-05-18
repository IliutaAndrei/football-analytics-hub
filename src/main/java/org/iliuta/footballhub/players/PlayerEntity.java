package org.iliuta.footballhub.players;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iliuta.footballhub.teams.TeamEntity;

import java.time.LocalDate;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    private Integer externalId;
    private String name;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String nationality;
    private String height;
    private String weight;
    private String position;
    private String photo;
}

package org.iliuta.footballhub.league;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<SeasonEntity, Integer> {

    Optional<SeasonEntity> findByLeagueAndYear(LeagueEntity league, Integer year);
}

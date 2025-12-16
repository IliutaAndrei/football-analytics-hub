package org.iliuta.footballhub.leagues;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeasonRepository extends JpaRepository<SeasonEntity, Integer> {

    Optional<SeasonEntity> findByLeagueAndYear(LeagueEntity league, Integer year);
    List<SeasonEntity> findByLeague_Id(Integer leagueId);
}

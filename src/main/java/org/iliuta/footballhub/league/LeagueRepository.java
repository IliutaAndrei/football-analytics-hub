package org.iliuta.footballhub.league;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Integer> {

    Optional<LeagueEntity> findByExternalId(Integer externalId);
}

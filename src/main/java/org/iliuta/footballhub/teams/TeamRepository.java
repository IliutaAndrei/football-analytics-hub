package org.iliuta.footballhub.teams;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    List<TeamEntity> findByLeague_IdAndSeason_Year(Integer leagueId, Integer seasonYear);

    Optional<TeamEntity> findByIdAndLeague_IdAndSeason_Year(
            Integer id,
            Integer leagueId,
            Integer seasonYear
    );

    Optional<TeamEntity> findByExternalId(Integer externalId);

    Optional<TeamEntity> findByExternalIdAndLeague_IdAndSeason_Year(
            Integer externalId,
            Integer leagueId,
            Integer seasonYear
    );
}

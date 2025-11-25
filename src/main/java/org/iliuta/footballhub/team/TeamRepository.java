package org.iliuta.footballhub.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    List<TeamEntity> findByLeague_ExternalIdAndSeason_Year(Integer leagueId, Integer seasonYear);
    Optional<TeamEntity> findByExternalId(Integer externalId);
}

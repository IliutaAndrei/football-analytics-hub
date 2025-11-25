package org.iliuta.footballhub.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    List<TeamEntity> findByLeague_IdAndSeason_Year(Integer leagueId, Integer seasonYear);
}

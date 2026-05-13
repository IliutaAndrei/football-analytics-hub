package org.iliuta.footballhub.players;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    Optional<PlayerEntity> findByExternalIdAndTeamId(Integer externalId, Integer teamId);
    List<PlayerEntity> findByTeamId(Integer teamId);
}

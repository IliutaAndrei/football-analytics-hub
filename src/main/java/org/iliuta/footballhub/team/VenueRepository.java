package org.iliuta.footballhub.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<VenueEntity, Integer> {

    Optional<VenueEntity> findByExternalId(Integer externalId);
}

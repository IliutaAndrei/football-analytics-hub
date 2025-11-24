package org.iliuta.footballhub.league;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Integer> {

    Optional<LeagueEntity> findByExternalId(Integer externalId);

    List<LeagueEntity> findByCountry_Code(String countryCode);
}

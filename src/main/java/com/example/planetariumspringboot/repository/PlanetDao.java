package com.example.planetariumspringboot.repository;

import com.example.planetariumspringboot.entities.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PlanetDao extends JpaRepository<Planet,Integer> {
    Optional<Planet> findByPlanetName(String planetName);
    Optional<List<Planet>> findByOwnerId(int ownerId);

    @Transactional
    @Modifying
    @Query(value = "insert into planets values(default, :planetName, :ownerId)", nativeQuery = true)
    void createPlanet(@Param("planetName") String planetName, @Param("ownerId") int ownerId);

    @Transactional
    @Modifying
    @Query(value = "update planets set planet_name = :planetName, owner_id = :ownerId where planet_id = :planetId", nativeQuery = true)
    int updatePlanet(@Param("planetName") String planetName, @Param("ownerId") int ownerId, @Param("planetId") int planetId);
}

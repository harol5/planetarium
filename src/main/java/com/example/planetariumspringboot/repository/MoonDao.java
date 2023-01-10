package com.example.planetariumspringboot.repository;

import com.example.planetariumspringboot.entities.Moon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MoonDao extends JpaRepository<Moon, Integer> {
    Optional<Moon> findByMoonName(String moonName);

    Optional<List<Moon>> findByMyPlanetId(int myPlanetId);

    @Transactional
    @Modifying
    @Query(value = "insert into moons values(default, :moonName, :myPlanetId)",nativeQuery = true)
    void createMoon(@Param("moonName") String moonName, @Param("myPlanetId") int myPlanetId);

    @Transactional
    @Modifying
    @Query(value = "update moons set moon_name = :moonName, my_planet_id = :myPlanetId where moon_id = :moonId", nativeQuery = true)
    int updateMoon(@Param("moonName") String moonName, @Param("myPlanetId") int myPlanetId, @Param("moonId") int moonId);

}

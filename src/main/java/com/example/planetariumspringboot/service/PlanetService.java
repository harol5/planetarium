package com.example.planetariumspringboot.service;

import com.example.planetariumspringboot.entities.Planet;
import com.example.planetariumspringboot.exceptions.EntityNotFound;
import com.example.planetariumspringboot.repository.PlanetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {
    @Autowired
    private PlanetDao planetDao;

    public Planet findPlanetById(int id){
        Optional<Planet> possiblePlanet = this.planetDao.findById(id);
        if(possiblePlanet.isPresent()){
            return possiblePlanet.get();
        }else{
            throw new EntityNotFound("Planet not found");
        }
    }

    public Planet findPlanetByName(String name){
        Optional<Planet> possiblePlanet = this.planetDao.findByPlanetName(name);
        if(possiblePlanet.isPresent()){
            return possiblePlanet.get();
        }else{
            throw new EntityNotFound("Planet not found");
        }
    }

    public List<Planet> findAllPlanets(){
        List<Planet> planets = this.planetDao.findAll();
        if (planets.size() != 0){
            return planets;
        }else {
            throw new EntityNotFound("planets not found");
        }
    }

    public List<Planet> findPlanetsByOwnerId(int id){
        Optional<List<Planet>> possibleOwnerPlanets = this.planetDao.findByOwnerId(id);
        if(possibleOwnerPlanets.isPresent() && possibleOwnerPlanets.get().size() != 0){
            return possibleOwnerPlanets.get();
        }else{
            throw new EntityNotFound("No user in database");
        }
    }

    public String createPlanet(String planetName, int ownerId){
        this.planetDao.createPlanet(planetName,ownerId);
        return "Planet created";
    }

    public String updatePlanet(String planetName, int ownerId, int planetId){
        int rowCount = this.planetDao.updatePlanet(planetName,ownerId,planetId);
        if (rowCount == 1){
            return "Player updated Successfully";
        }else{
            throw new EntityNotFound("Could not update planet");
        }
    }

    public String deletePlanet(int id){
        this.planetDao.deleteById(id);
        return "Planet deleted successfully";
    }
}

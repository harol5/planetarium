package com.example.planetariumspringboot.service;

import com.example.planetariumspringboot.entities.Moon;
import com.example.planetariumspringboot.exceptions.EntityNotFound;
import com.example.planetariumspringboot.repository.MoonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoonService {
    @Autowired
    private MoonDao moonDao;
    public Moon findMoonById(int id){
        Optional<Moon> possibleMoon = this.moonDao.findById(id);
        if(possibleMoon.isPresent()){
            return possibleMoon.get();
        }else {
            throw new EntityNotFound("Moon not found");
        }
    }
    public Moon findMoonByName(String moonName){
        Optional<Moon> possibleMoon = this.moonDao.findByMoonName(moonName);
        if(possibleMoon.isPresent()){
            return possibleMoon.get();
        }else{
            throw new EntityNotFound("Moon not found");
        }
    }

    public List<Moon> findAllMoons(){
        List<Moon> moons = this.moonDao.findAll();
        if(moons.size() != 0){
            return moons;
        }else{
            throw new EntityNotFound("No moons in the database");
        }
    }

    public List<Moon> findMoonsByPlanet(int planetId){
        Optional<List<Moon>> possibleMoons = this.moonDao.findByMyPlanetId(planetId);
        if(possibleMoons.isPresent() && possibleMoons.get().size() != 0){
            return possibleMoons.get();
        }else{
            throw new EntityNotFound("No moons related to planet");
        }
    }

    public String createMoon(String moonName, int myPlanetId){
        this.moonDao.createMoon(moonName,myPlanetId);
        return "Moon created";
    }

    public String updateMoon(String moonName, int myPlanetId, int moonId){
        int rowCount = this.moonDao.updateMoon(moonName,myPlanetId,moonId);
        if (rowCount == 1) {
            return "Moon updated successfully";
        }else{
            throw new EntityNotFound("Could not update moon");
        }
    }

    public String deleteMoon(int moonId){
        this.moonDao.deleteById(moonId);
        return "Moon deleted successfully";
    }
}

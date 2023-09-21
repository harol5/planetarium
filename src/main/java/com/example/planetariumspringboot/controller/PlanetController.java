package com.example.planetariumspringboot.controller;

import com.example.planetariumspringboot.entities.Planet;
import com.example.planetariumspringboot.exceptions.AuthenticationFailed;
import com.example.planetariumspringboot.exceptions.EntityNotFound;
import com.example.planetariumspringboot.service.PlanetService;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // this tells Spring we are using this class as a controller to handle http requests & responses
public class PlanetController {

    public static Logger planetLogger = LoggerFactory.getLogger(PlanetController.class);

    @Autowired
    private PlanetService planetService;

    /*
     * Use exception handling anytime you have a potential recurring issue
     * like a non-existent entity being referenced
     */

    @ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenticationFailed(AuthenticationFailed e){
        planetLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e){
        planetLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e){
        planetLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> deleteFailed(EmptyResultDataAccessException e){
        planetLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>("could not delete player", HttpStatus.BAD_REQUEST);
    }

    //-------------------------------------------------------------------

    @GetMapping("/api/planet/id/{id}")
    public ResponseEntity<Planet> findPlanetById(@PathVariable int id){
        return new ResponseEntity<>(this.planetService.findPlanetById(id), HttpStatus.OK);
    }
    @GetMapping("api/planet/{name}")
    public ResponseEntity<Planet> findPlanetByName(@PathVariable String name){
        return new ResponseEntity<>(this.planetService.findPlanetByName(name), HttpStatus.OK);
    }
    @GetMapping("api/planet")
    public ResponseEntity<List<Planet>> findAllPlanets(){
        return new ResponseEntity<>(this.planetService.findAllPlanets(), HttpStatus.OK);
    }
    @GetMapping("api/planet/user/{id}")
    public ResponseEntity<List<Planet>> findPlanetsByOwnerId(@PathVariable int id){
        return new ResponseEntity<>(this.planetService.findPlanetsByOwnerId(id), HttpStatus.OK);
    }
    @PostMapping("api/planet")
    public ResponseEntity<String> createPlanet(@RequestBody Planet planet){
        String message = this.planetService.createPlanet(planet.getPlanetName(), planet.getOwnerId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PatchMapping("api/planet")
    public ResponseEntity<String> updatePlanet(@RequestBody Planet planet){
        String message = this.planetService.updatePlanet(planet.getPlanetName(),planet.getOwnerId(),planet.getPlanetId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("api/planet/{id}")
    public ResponseEntity<String> deletePlanet(@PathVariable int id){
        return new ResponseEntity<>(this.planetService.deletePlanet(id),HttpStatus.OK);
    }

    //to get 500 status code
    @GetMapping("/uhoh")
    public ResponseEntity<String> returnFiveHundered(){
        return new ResponseEntity<>("returning 500 status code", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

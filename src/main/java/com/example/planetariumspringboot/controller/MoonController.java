package com.example.planetariumspringboot.controller;

import com.example.planetariumspringboot.entities.Moon;
import com.example.planetariumspringboot.exceptions.AuthenticationFailed;
import com.example.planetariumspringboot.exceptions.EntityNotFound;
import com.example.planetariumspringboot.service.MoonService;
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
public class MoonController {

    public static Logger moonLogger = LoggerFactory.getLogger(MoonController.class);

    @Autowired
    private MoonService moonService;

    /*
     * Use exception handling anytime you have a potential recurring issue
     * like a non-existant entity being referenced
     */

    @ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenticationFailed(AuthenticationFailed e){
        moonLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e){
        moonLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> psqlException(PSQLException e){
        moonLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> deleteIssue(EmptyResultDataAccessException e){
        moonLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>("could not delete player", HttpStatus.BAD_REQUEST);
    }
    //--------------------------------------------------------------------

    @GetMapping("api/moon/id/{id}")
    public ResponseEntity<Moon> findMoonById(@PathVariable int id){
        return new ResponseEntity<>(this.moonService.findMoonById(id), HttpStatus.OK);
    }

    @GetMapping("api/moon/{name}")
    public ResponseEntity<Moon> findMoonByName(@PathVariable String name){
        return new ResponseEntity<>(this.moonService.findMoonByName(name), HttpStatus.OK);
    }

    @GetMapping("api/moon")
    public ResponseEntity<List<Moon>> findAllMoons(){
        return new ResponseEntity<>(this.moonService.findAllMoons(),HttpStatus.OK);
    }

    @GetMapping("api/planet/{id}/moons")
    public ResponseEntity<List<Moon>> findMoonsByPlanet(@PathVariable int id){
        return new ResponseEntity<>(this.moonService.findMoonsByPlanet(id), HttpStatus.OK);
    }

    @PostMapping("api/moon")
    public ResponseEntity<String> createMoon(@RequestBody Moon moon){
        String message = this.moonService.createMoon(moon.getMoonName(),moon.getMyPlanetId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping("api/moon")
    public ResponseEntity<String> updateMoon(@RequestBody Moon moon){
        String message = this.moonService.updateMoon(moon.getMoonName(),moon.getMyPlanetId(),moon.getMoonId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("api/moon/{id}")
    public ResponseEntity<String> deleteMoon(@PathVariable int id){
        String message = this.moonService.deleteMoon(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

package com.example.planetariumspringboot.controller;

import com.example.planetariumspringboot.entities.User;
import com.example.planetariumspringboot.service.UserService;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController // this tells Spring we are using this class as a controller to handle http requests & responses
public class AuthenticateController {

    public static Logger authLogger = LoggerFactory.getLogger(AuthenticateController.class);

    @Autowired
    private UserService userService;

    /*
     * Use exception handling anytime you have a potential recurring issue
     * like a non-existent entity being referenced
     */

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> psqlException(PSQLException e){
        authLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> something(EmptyResultDataAccessException e){
        authLogger.error(e.getLocalizedMessage(),e);
        return new ResponseEntity<>("something wrong happened!!", HttpStatus.BAD_REQUEST);
    }

    //----------------------------------------------------------------------------

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User newUser){
        String message = this.userService.createUser(newUser);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String name){
        return new ResponseEntity<>(this.userService.findUserByUsername(name),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpSession session){
        String message = this.userService.validate(session, user);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "logged out";
    }

}

package com.example.planetariumspringboot.exceptions;

public class AuthenticationFailed extends RuntimeException{
    public AuthenticationFailed(String message){
        super(message);
    }
}

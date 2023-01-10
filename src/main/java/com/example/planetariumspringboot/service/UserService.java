package com.example.planetariumspringboot.service;

import com.example.planetariumspringboot.entities.User;
import com.example.planetariumspringboot.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public String createUser(User newUser){
        this.userDao.createUser(newUser.getUsername(),newUser.getPassword());
        return "User created";
    }

    public User findUserByUsername(String name){
        Optional<User> possibleUser = this.userDao.findByUsername(name);
        if(possibleUser.isPresent()){
            return possibleUser.get();
        }else {
            return new User();
        }
    }

    public String validate(HttpSession session, User user){
        Optional<User> possibleUser = this.userDao.findByUsername(user.getUsername());

        if(possibleUser.isPresent() && possibleUser.get().getPassword().equals(user.getPassword())){
            session.setAttribute("user", possibleUser.get());
            return "logged in successfully";
        }else {
            return "not logged in";
        }
    }

}

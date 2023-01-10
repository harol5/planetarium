package com.example.planetariumspringboot.config;

import com.example.planetariumspringboot.exceptions.AuthenticationFailed;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null){
            return true;
        }else {
            throw new AuthenticationFailed("Please loggin first to interact with the application");
        }
    }
}

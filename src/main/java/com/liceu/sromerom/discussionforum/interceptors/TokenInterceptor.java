package com.liceu.sromerom.discussionforum.interceptors;

import com.liceu.sromerom.discussionforum.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String[] notRequiredAuthURLs = new String[]{"/getprofile", "/login", "/register"};
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        System.out.println("Header: " + header);
        /*
        System.out.println("Metodo actual: " + request.getMethod());
        if (request.getMethod().equals("GET") || notNeedLogin(request)) {
            System.out.println("Hacemos expcecion...");
            return true;
        }


        if (header == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
         */
        if (header == null) {
            System.out.println("No tienes permiso bro");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        if (header.equals("Bearer null")) {
            System.out.println("Se ha enviado el bearer pero vacio!");
            return true;
        }
        try {
            System.out.println("Hay token!!!!!!!!!");
            String token = header.replace("Bearer ", "");
            String user = tokenService.getSubject(token);
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private boolean notNeedLogin(HttpServletRequest req) {
        String actualURL = req.getRequestURL().toString();

        for (String loginRequiredURL : notRequiredAuthURLs) {
            if (actualURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }
}

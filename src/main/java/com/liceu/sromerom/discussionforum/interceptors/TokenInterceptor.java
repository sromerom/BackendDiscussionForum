package com.liceu.sromerom.discussionforum.interceptors;

import com.liceu.sromerom.discussionforum.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    String [] authorizationUrlByGET = new String[]{"/getprofile"};

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) return true;
        String header = request.getHeader("Authorization");


        if (request.getMethod().equals("GET") && !needLogin(request)) {
            return true;
        }

        if (header == null || header.equals("Bearer null")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try {
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

    private boolean needLogin(HttpServletRequest req) {
        String actualURL = req.getRequestURL().toString();

        for (String loginRequiredURL : authorizationUrlByGET) {
            if (actualURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }
}

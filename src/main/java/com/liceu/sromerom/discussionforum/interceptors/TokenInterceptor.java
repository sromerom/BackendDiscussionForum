package com.liceu.sromerom.discussionforum.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    String [] authorizationUrlByGET = new String[]{"/getprofile"};

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) return true;
        String header = request.getHeader("Authorization");

        //No fa falta autentificació, token jwt
        if (request.getMethod().equals("GET") && !needLogin(request)) {
            return true;
        }

        //Es fa un request a un recurs que necessita token y no s'ha trobat
        if (header == null || header.equals("Bearer null")) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized");
            return false;
        }

        //S'ha trobat correctament el token, es procedeix a aconseguir el subject d'aquest
        try {
            String token = header.replace("Bearer ", "");
            Map<String, Claim> claimUser = tokenService.getSubject(token);
            request.setAttribute("user", claimUser);
        } catch (Exception e) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized");
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

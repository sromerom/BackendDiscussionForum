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



        if (request.getMethod().equals("GET") && !needLogin(request)) {
            System.out.println("No hace falta autentificacion");
            return true;
        }

        if (header == null || header.equals("Bearer null")) {
            System.out.println("Intenta hacer un request que require de token y no se ha encontrado. UNAUTHORIZED");
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized");
            return false;
        }
        try {
            System.out.println("Todo correcto!");
            String token = header.replace("Bearer ", "");
            Map<String, Claim> claimUser = tokenService.getSubject(token);
            //System.out.println("Email del claim!!! " + claimUser.get("email").as(String.class));
            request.setAttribute("user", claimUser);
        } catch (Exception e) {
            e.printStackTrace();
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

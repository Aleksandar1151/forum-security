package com.forumsecurity.forum.security;

import com.forumsecurity.forum.services.JWTService;
import com.forumsecurity.forum.services.LoggingService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;


@Component
public class JWTFilter implements Filter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LoggingService loggingService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {



        loggingService.log(response.toString());

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authorizationHeader = httpRequest.getHeader("Authorization");




        if (
                httpRequest.getRequestURI().equals("/api/forums/login") ||
                httpRequest.getRequestURI().equals("/api/forums/admin/users") ||
                httpRequest.getRequestURI().equals("/api/forums/validateToken")||
                        httpRequest.getRequestURI().equals("/api/forums/sendmail/*")||
                        httpRequest.getRequestURI().equals("/api/forums/sendmail")
                       // httpRequest.getRequestURI().equals("/api/forums")

        )
        {
            chain.doFilter(request, response);
            return;
        }



        String token = null;
        String username = null;

        // Provera da li Authorization header sadrži "Bearer " token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtService.getUsernameFromToken(token); // Dobijanje korisničkog imena iz tokena
        }

        // Ako je korisničko ime prisutno i autentifikacija nije već postavljena
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response); // Propuštanje zahteva dalje u lancu filtera
    }
}

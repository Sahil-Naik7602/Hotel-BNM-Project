package com.example.hotelbnmproject.security;

import com.example.hotelbnmproject.entity.User;
import com.example.hotelbnmproject.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            final String requestHeaderToken  = request.getHeader("Authorization");
            if (requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }
            String token = requestHeaderToken.split("Bearer ")[1];
            System.out.println(token);
            Long id = jwtService.getUserIdFromToken(token);

            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User userEntity = userService.findUserById(id);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userEntity,null,userEntity.getAuthorities());

                //this is to log the details of ip address and other details later
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request,response);

        }catch(Exception ex) {
            /**
             * See this HandlerExceptionResolver is basically used so that the errors
             * that are getting created before entering into the dispatcher servlet
             * those are moved into the SecurityContextHolder so that our GlobalExceptionHolder advice
             * can recognise them
             */
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }
}

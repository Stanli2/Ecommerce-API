package com.ike.ecommerce.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ike.ecommerce.model.LocalUser;
import com.ike.ecommerce.model.dao.LocalUserDao;
import com.ike.ecommerce.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private LocalUserDao localUserDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenheader = request.getHeader("Authorization");
        if(tokenheader != null && tokenheader.startsWith("Bearer ")) {
            String token = tokenheader.substring(7);
            try{
                String username = jwtService.getUsername(token);
                Optional<LocalUser> userDao = localUserDao.findByUsernameContainingIgnoreCase(username);
                if (userDao.isPresent()) {
                    LocalUser user = userDao.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (JWTDecodeException e){
            }
        }
        filterChain.doFilter(request, response);
    }
}

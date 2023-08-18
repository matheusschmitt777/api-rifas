package com.api.rifas.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.rifas.repositories.UserAdminRepository;
import com.api.rifas.servies.config.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
    UserAdminRepository userAdminRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    var token = this.recoverToken(request);

	    if (token != null) {
	        var login = tokenService.validateToken(token);
	        UserDetails userAdmin = userAdminRepository.findByLogin(login);

	        var authentication = new UsernamePasswordAuthenticationToken(userAdmin, null, userAdmin.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        // Adicione o token no cabeçalho da resposta
	        response.addHeader("Authorization", "Bearer " + token);
	    }

	    filterChain.doFilter(request, response);
	}

	
	private String recoverToken(HttpServletRequest request){
		var authHeader = request.getHeader("Authorization");
		if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}

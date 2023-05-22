package com.nishanneupane.whatsapp.config;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;

public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException ,BadCredentialsException{

		String jwt = request.getHeader(JwtConstant.JWT_HEADER);

		if (jwt != null) {
			try {
				// Bearer token
				
				jwt = jwt.substring(7);
				
				SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				Claims claim=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String username=String.valueOf(claim.get("email"));
				String authorities=String.valueOf(claim.get("authorities"));
				
				List<GrantedAuthority> auths=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				Authentication authentication=new UsernamePasswordAuthenticationToken(username, null,auths);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
			} catch (BadCredentialsException e) {
				throw new BadCredentialsException("Invalid received ....");
			}
		}
		
		filterChain.doFilter(request, response);
	}
	

}

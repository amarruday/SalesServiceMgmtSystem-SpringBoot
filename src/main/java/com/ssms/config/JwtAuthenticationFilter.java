package com.ssms.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssms.constants.JwtUtils;
import com.ssms.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsServiceImpl userDetailsService;
	private final JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				if (jwtToken != null && !jwtToken.equals("") && !jwtUtils.isTokenExpired(jwtToken)) {

					username = this.jwtUtils.extractUsername(jwtToken);
					// Validate token
					if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
						if (this.jwtUtils.validateToken(jwtToken, userDetails)) {
							// token is valid

							UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
							authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							// Now Set Authentication to SecurityContextHolder
							SecurityContextHolder.getContext().setAuthentication(authentication);
						}
						
					} else {
						System.out.println("Invalid Token...!");
					}
				} else {
					System.out.println("Token is expired or Not exists!");
				}
			} catch (ExpiredJwtException e) {
				System.out.println("Token Expired..! ");
				e.printStackTrace();
			} catch (Exception e) {
				throw e;
				// e.printStackTrace();
			}
		}
		filterChain.doFilter(request, response);
	}// method

}

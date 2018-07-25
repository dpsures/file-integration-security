package org.poc.app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

public class CsrfTokenFilter extends OncePerRequestFilter {

	private final String X_CSRF_TOKEN = "X-CSRF-TOKEN";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String csrfToken = request.getHeader(X_CSRF_TOKEN) != null ? request.getHeader(X_CSRF_TOKEN).toString() : null;

		// Simple CSRF token validation
		// if csrf token is null from request header then restrict the access
		if(csrfToken == null){
			throw new AccessDeniedException("Not Authorized");
		}

		filterChain.doFilter(request, response);
	}

}

package com.staff.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import static org.springframework.util.StringUtils.hasText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtFilter extends GenericFilterBean{
	public static final String AUTHORIZATION = "Authorization";
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String token = getTokenFromRequest((HttpServletRequest) servletRequest);
		System.out.println(token);
		if (token != null && jwtProvider.validateToken(token)) {
			String userLogin = jwtProvider.getUsernameFromToken(token);
			CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader(AUTHORIZATION);
		if (hasText(bearer) && bearer.startsWith("Bearer")) {
			return bearer.substring(7);
		}
		return null;
	}

}
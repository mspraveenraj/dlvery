package com.project.dlvery;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FilterToAddHeaders extends OncePerRequestFilter {

	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {

        response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
		response.addHeader("X-Total-Count", String.valueOf(20));
		//response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		//response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		//response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        filterChain.doFilter(request, response);
    }
	
}

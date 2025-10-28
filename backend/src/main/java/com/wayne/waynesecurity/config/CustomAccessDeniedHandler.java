package com.wayne.waynesecurity.config;

import java.io.IOException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired; // NOVO
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayne.waynesecurity.controllers.exceptions.StandardError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
	private ObjectMapper objectMapper; 

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		HttpStatus status = HttpStatus.FORBIDDEN;
		String error = "Acesso Negado à URL";
		String message = "Você não tem a permissão necessária para acessar este recurso. " + accessDeniedException.getMessage();

		StandardError standardError = new StandardError(Instant.now(), status.value(), error, message,
				request.getRequestURI());

		response.setStatus(status.value());
		response.setContentType("application/json"); 
		response.getWriter().write(objectMapper.writeValueAsString(standardError));
	}
}
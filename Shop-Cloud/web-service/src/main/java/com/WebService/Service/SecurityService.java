package com.WebService.Service;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.WebService.Client.SecurityFeignClient;

@Service
public class SecurityService {
	
	@Autowired
	private SecurityFeignClient securityFeignClient;

	public boolean VailidateJwtToken(String token) {
		
		ResponseEntity<Map<String, Object>> response = securityFeignClient.JwtTokenVailidator(token);
		
		if(response.getStatusCode().is2xxSuccessful()) {
			Authentication authertication = new UsernamePasswordAuthenticationToken(
					response.getBody().get("username"),
					null,
					Arrays.asList(new SimpleGrantedAuthority((String) response.getBody().get("username"))));
			SecurityContextHolder.getContext().setAuthentication(authertication);
			return true;
		}else {
			return false;
		}
}
}

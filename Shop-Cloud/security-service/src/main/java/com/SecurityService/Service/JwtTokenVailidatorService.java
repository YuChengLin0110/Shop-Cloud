package com.SecurityService.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Service
public class JwtTokenVailidatorService {
	
	private String jwtKey = "JwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKeyJwtKey";
	
	public ResponseEntity<Map<String, Object>> vaildateJwtToken(String token) {

		Claims claims = Jwts
				.parser()
				.setSigningKey(jwtKey.getBytes())
				.parseClaimsJws(token)
				.getBody();
		
		Map<String, Object> response = new HashMap<String,Object>();
		response.put("username", claims.get("user_name"));
		response.put("authorities", claims.get("authorities"));
		
		return ResponseEntity.ok(response);
	}
}

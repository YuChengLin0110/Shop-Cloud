package com.SecurityService.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SecurityService.Service.JwtTokenVailidatorService;

@RestController
public class SecurityFeignController {

	@Autowired
	JwtTokenVailidatorService jwtTokenVailidatorService;
	
	
	@PostMapping("/JwtTokenVailidator")
	ResponseEntity<Map<String, Object>> JwtTokenVailidator(@RequestParam("token") String token) {
		
		return jwtTokenVailidatorService.vaildateJwtToken(token);
	}

}

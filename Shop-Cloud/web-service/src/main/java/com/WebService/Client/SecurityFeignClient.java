package com.WebService.Client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("security-service")
public interface SecurityFeignClient {

	@PostMapping("/JwtTokenVailidator")
	ResponseEntity<Map<String, Object>> JwtTokenVailidator(@RequestParam("token") String token);
	
	@PostMapping("/oauth/revoke")
	String tokenRevoke(@RequestParam("token") String token);
	
	@PostMapping("/logout")
	void logout();
}

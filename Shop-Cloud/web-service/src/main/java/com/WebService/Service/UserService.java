package com.WebService.Service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.WebService.Client.SecurityFeignClient;

@Service
public class UserService {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SecurityFeignClient securityFeignClient;
	
	public void logout(String token) {
		securityFeignClient.tokenRevoke(token);
		securityFeignClient.logout();
	}
	
	public Authentication getUserAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		return auth;
	}
	
	public String getUsername() {
		
		return this.getUserAuth().getName();
	}
	
	public String getUserToken() {
		
		return (String) session.getAttribute("token");
	}
	
	public boolean isLogin() {
		
		Authentication auth = this.getUserAuth();
		
		if(auth!= null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
			return true;
		}else {
			return false;
		}
	}
}

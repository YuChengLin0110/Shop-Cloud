package com.WebService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WebService.Model.UserBean;

@FeignClient("user-service")
public interface UserFeignClient {
	
	@PostMapping("/doLogin")
	@ResponseBody
	String doLogin(@RequestParam("username") String username, 
				   @RequestParam("password") String password);
	
	@PostMapping("/findUserByAccount")
	@ResponseBody
	UserBean findByAccount(@RequestParam("username") String username);
	
	@PostMapping("/checkAccount")
	@ResponseBody
	public boolean checkAccount(@RequestParam("account") String account);
	
	@PostMapping("/userUpdate")
	public ResponseEntity<Void> userUpdate(
							   @RequestParam("valName") String name,
							   @RequestParam("valAddr") String addr,
							   @RequestParam("valTel") String tel,
							   @RequestParam("valEmail") String email,
							   @RequestParam("valUser") String account);

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestParam("valUser") String account, @RequestParam("valPwd") String password,
			@RequestParam("valName") String name, @RequestParam("valAddr") String addr,
			@RequestParam("valTel") String tel, @RequestParam("valEmail") String email);
}

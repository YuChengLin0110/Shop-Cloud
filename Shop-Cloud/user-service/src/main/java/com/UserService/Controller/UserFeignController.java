package com.UserService.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.UserService.Model.UserBean;
import com.UserService.Service.UserService;


@Controller
public class UserFeignController {
	
	@Autowired
	private UserService userService;

	
	@PostMapping("/findUserByAccount")
	@ResponseBody
	public UserBean findUserByAccont(@RequestParam("username") String account) {
		
		return userService.findByAccount(account);
	}
	
	@PostMapping("/doLogin")
	@ResponseBody
	public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {

		String token = userService.login(username, password);
		JSONObject jsonObject = new JSONObject(token);
		String accessToken = jsonObject.getString("access_token");

		return accessToken;
	}
	
	@PostMapping("/userUpdate")
	public ResponseEntity<Void> userUpdate(
			@RequestParam("valName") String name, @RequestParam("valAddr") String addr,
			@RequestParam("valTel") String tel, @RequestParam("valEmail") String email,@RequestParam("valUser") String account) {

		
		try {
			userService.update(name, addr, tel, email, account);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestParam("valUser") String account, @RequestParam("valPwd") String password,
			@RequestParam("valName") String name, @RequestParam("valAddr") String addr,
			@RequestParam("valTel") String tel, @RequestParam("valEmail") String email) {
	
		try {
			userService.register(account, password, name, addr, tel, email);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// Ajax用來檢查帳號是否重複
	@PostMapping("/checkAccount")
	@ResponseBody
	public boolean checkAccount(@RequestParam("account") String account) {
		boolean isExist = false;
		// Ajax傳回來資料命名為data

		if (userService.findByAccount(account) != null) {
			isExist = true;
		}
		return isExist;
	}
}

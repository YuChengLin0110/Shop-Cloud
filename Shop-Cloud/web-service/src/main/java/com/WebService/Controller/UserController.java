package com.WebService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WebService.Client.UserFeignClient;
import com.WebService.Service.SecurityService;
import com.WebService.Service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userService;

	// Ajax用來檢查帳號是否重複
		@PostMapping("/checkAccount")
		@ResponseBody
		public boolean checkAccount(@RequestParam("account") String account) {
			boolean isExist = userFeignClient.checkAccount(account);
			return isExist;
		}
		
		@PostMapping("/register")
		public String register(@RequestParam("valUser") String account, @RequestParam("valPwd") String password,
				@RequestParam("valName") String name, @RequestParam("valAddr") String addr,
				@RequestParam("valTel") String tel, @RequestParam("valEmail") String email) {

			userFeignClient.register(account, password, name, addr, tel, email);
			return "redirect:/login";
		}
		
		@PostMapping("/doLogin")
		public String doLogin(@RequestParam("username") String username, 
							  @RequestParam("password") String password) {
			
			String accessToken = userFeignClient.doLogin(username, password);
			if(securityService.VailidateJwtToken(accessToken)) {

				return "redirect:/";
			}else {
				return "login";
			}
		}
		
		@PostMapping("/logout")
		public String logout() {
			String token = userService.getUserToken();
			userService.logout(token);
			SecurityContextHolder.clearContext();

			return "redirect:/";
		}
		
		
		@PostMapping("/userUpdate")
		public String userUpdate(
				@RequestParam("valName") String name, @RequestParam("valAddr") String addr,
				@RequestParam("valTel") String tel, @RequestParam("valEmail") String email, @RequestParam("valUser") String account) {

			userFeignClient.userUpdate(name, addr, tel, email, account);
			
			return "redirect:/user";
		}
}

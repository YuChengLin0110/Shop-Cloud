package com.UserService.Service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.UserService.Client.SecurityFeignClient;
import com.UserService.DAO.UserDAO;
import com.UserService.Model.UserBean;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SecurityFeignClient securityFeignClient;

	
	@Value("${spring.security.oauth2.client.registration.my-client-id.client-id}")
	private String clientId;
	
	@Value("${spring.security.oauth2.client.registration.my-client-id.client-secret}")
	private String clientSecret;

	
		public String login(@RequestParam("username") String username,
                						  @RequestParam("password") String password){
			
			String oauth2Client = clientId+":"+clientSecret;
			String base64Client = Base64.getEncoder().encodeToString(oauth2Client.getBytes());

			return securityFeignClient.login(username,password,"password","Basic "+base64Client);			
		}
	
		public void register(String account, String password, String name, String addr, String tel, String email) {

			if (userDAO.findByAccount(account) == null) {

				String bcryptPwd = passwordEncoder.encode(password);

				userDAO.addAccount(account, bcryptPwd, name, addr, tel, email);
			}
		}
		
		public void logout(String token) {
			securityFeignClient.tokenRevoke(token);
			securityFeignClient.logout();
		}

		public void update(String name, String addr, String tel, String email, String account) {

			userDAO.update(name, addr, tel, email, account);
		}



		public UserBean findByAccount(String account) {
			return userDAO.findByAccount(account);
		}
		

}

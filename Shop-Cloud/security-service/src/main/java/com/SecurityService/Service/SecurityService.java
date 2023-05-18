package com.SecurityService.Service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SecurityService.Client.UserFeignClient;
import com.SecurityService.Model.UserBean;

@Service
public class SecurityService implements UserDetailsService {

	@Autowired
	private UserFeignClient userDaoFeignClient;
	
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserBean userBean = userDaoFeignClient.findByAccount(username);

		if (userBean == null) {
			throw new UsernameNotFoundException("未找到使用者 ： " + username);
		} else {
			return new User(userBean.getAccount(), userBean.getPassword(),Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		}

	}

}

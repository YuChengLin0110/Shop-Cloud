package com.SecurityService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SecurityService.Model.UserBean;

@FeignClient("user-service")
public interface UserFeignClient {

	@PostMapping("/findUserByAccount")
	@ResponseBody
	UserBean findByAccount(@RequestParam("username") String account);
}

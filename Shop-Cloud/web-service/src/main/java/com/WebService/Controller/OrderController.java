package com.WebService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.WebService.Client.OrderFeignClient;
import com.WebService.Service.UserService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderFeignClient orderFeignClient;
	@Autowired
	private UserService userService;
	
	@PostMapping("/orderAdd")
	public String orderAdd(@RequestParam("valCartId") List<Long> cart_id) {
		
		String account = userService.getUsername();
		
		orderFeignClient.orderAdd(cart_id, account);
		
		return "redirect:/order";
		
	}
}

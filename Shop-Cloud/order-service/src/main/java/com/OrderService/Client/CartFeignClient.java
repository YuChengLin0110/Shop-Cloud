package com.OrderService.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("cart-service")
public interface CartFeignClient {
	
	@PostMapping("/cartBuy")
	@ResponseBody
	public boolean cartBuy( @RequestParam("valCartId") List<Long> cart_id);

}

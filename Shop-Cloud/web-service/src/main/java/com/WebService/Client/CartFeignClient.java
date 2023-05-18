package com.WebService.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WebService.Model.CartVO;


@FeignClient("cart-service")
public interface CartFeignClient {

	@PostMapping("/findCartVoByAccount")
	@ResponseBody
	public List<CartVO> findCartVoByAccount(@RequestParam("username") String username);
	
	@PostMapping("/cartAdd")
	@ResponseBody
	public boolean cartAdd(@RequestParam("pid") Long pid,
					@RequestParam("account") String account,
					@RequestParam("count") int count);
	
	@PostMapping("/cartUpdate")
	public ResponseEntity<Void> cartUpdate(@RequestParam int quantity,
			   			 @RequestParam Long id);
	
	@PostMapping("/cartBuy")
	@ResponseBody
	public boolean cartBuy( @RequestParam("valCartId") List<Long> cart_id);
	
	@PostMapping("/cartDelete")
	public ResponseEntity<Void> cartDelete(@RequestParam("valCartId") List<Long> cart_id);
	
}



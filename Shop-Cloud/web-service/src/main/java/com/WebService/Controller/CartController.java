package com.WebService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WebService.Client.CartFeignClient;

@Controller
public class CartController {
	
	@Autowired
	private CartFeignClient cartFeignClient;

	//Ajax 加入購物車
			@PostMapping("/cartAdd")
			@ResponseBody
			public boolean cartAdd(@RequestParam("pid") Long pid,
							@RequestParam("account") String account,
							@RequestParam("count") int count) {
				
				boolean flag = false;
				if(cartFeignClient.cartAdd(pid, account, count)==true) {
					flag = true;
					return flag;
				}else {
					flag = false;
					return flag;
				}	
			}
			
			@PostMapping("/cartUpdate/{id}")
			public String update(@RequestParam int quantity,
					   			 @PathVariable Long id) {
				
				cartFeignClient.cartUpdate(quantity, id);
				
				return "redirect:/cart";
				
			}
			
			@PostMapping("/cartBuy")
			public String buy( @RequestParam("valCartId") List<Long> cart_id) {
				
					if(cartFeignClient.cartBuy(cart_id)==true) {
						return "redirect:/cart";
					}else
						return "redirect:/cart";
			}
			
			@PostMapping("/cartDelete")
			public String delete(@RequestParam("valCartId") List<Long> cart_id) {

					cartFeignClient.cartDelete(cart_id);
				
				return "redirect:/cart";
			}
}

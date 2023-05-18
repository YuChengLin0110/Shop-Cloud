package com.CartService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CartService.Model.CartVO;
import com.CartService.Service.CartService;

@Controller
public class CartFeignController {
	
	@Autowired
	CartService cartService;

	@PostMapping("/findCartVoByAccount")
	@ResponseBody
	public List<CartVO> findCartVoByAccount(@RequestParam("username") String username) {
		return cartService.findCartVOByAccount(username);
	}
	
	//Ajax 加入購物車
		@PostMapping("/cartAdd")
		@ResponseBody
		public boolean add(@RequestParam("pid") Long pid,
						@RequestParam("account") String account,
						@RequestParam("count") int count) {
			System.out.println("cartservice" + account);
			boolean flag = false;
			if(cartService.add(account, pid, count)==true) {
				flag = true;
				return flag;
			}else {
				flag = false;
				return flag;
			}
			
		}
		
		
		@PostMapping("/cartUpdate")
		public ResponseEntity<Void> update(@RequestParam int quantity,
				   			 @RequestParam Long id) {
			
			try {
				cartService.update(quantity, id);
				return ResponseEntity.ok().build();
			}catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		
		@PostMapping("/cartBuy")
		@ResponseBody
		public boolean buy( @RequestParam("valCartId") List<Long> cart_id) {
			
				return cartService.buy(cart_id);
		}
		
		@PostMapping("/cartDelete")
		public ResponseEntity<Void> delete(@RequestParam("valCartId") List<Long> cart_id) {
			
			try {
				cartService.delete(cart_id);
				return ResponseEntity.ok().build();
			}catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
				
			
		}
}

package com.OrderService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.OrderService.Model.OrderDetailVO;
import com.OrderService.Model.OrderVO;
import com.OrderService.Service.OrderService;

@Controller
public class OrderFeignController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/order")
	@ResponseBody
	public List<OrderVO> findOrderVoByAccount(@RequestParam("username") String username) {
		
		return orderService.findOrderVOByAccount(username);
	}
	
	@GetMapping("/orderDetail")
	@ResponseBody
	public List<OrderDetailVO> findOrderDetailVoByOrderNumber(@RequestParam("username") String account, @RequestParam("orderNumber") String order_number){
		return orderService.findOrderDetailVOByOrderNumber(account, order_number);
	}
	
	@PostMapping("/findOrderByOrdernumber")
	public OrderVO findOrderByOrdernumber(@RequestParam String ordernumber) {
		return orderService.findOrderByOrderNumber(ordernumber);
	}
	
	@PostMapping("/orderAdd")
	public ResponseEntity<Void> orderAdd(@RequestParam("valCartId") List<Long> cart_id,
						   @RequestParam("username") String username) {
		
		try {		
			orderService.addOrder(username, cart_id);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/updateOrderStatus")
	public void updateOrderStatus(@RequestParam int rtnCode, @RequestParam String orderNumber) {
		orderService.updateOrderStatus(rtnCode, orderNumber);
	}
	
}

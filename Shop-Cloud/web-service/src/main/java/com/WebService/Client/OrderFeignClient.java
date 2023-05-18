package com.WebService.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WebService.Model.OrderDetailVO;
import com.WebService.Model.OrderVO;

@FeignClient("order-service")
public interface OrderFeignClient {

	@GetMapping("/order")
	@ResponseBody
	public List<OrderVO> findOrderVoByAccount(@RequestParam("username") String username);
	
	@GetMapping("/orderDetail")
	@ResponseBody
	public List<OrderDetailVO> findOrderDetailVoByOrderNumber(@RequestParam("username") String username, @RequestParam("orderNumber") String order_number);
	
	@PostMapping("/orderAdd")
	public ResponseEntity<Void> orderAdd(@RequestParam("valCartId") List<Long> cart_id,
						   @RequestParam("username") String username);
	
	@PostMapping("/findOrderByOrdernumber")
	public OrderVO findOrderByOrdernumber(@RequestParam String ordernumber);
	
	@PostMapping("/updateOrderStatus")
	public void updateOrderStatus(@RequestParam int rtnCode, @RequestParam String orderNumber);
	}
package com.CartService.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CartService.Model.ProductBean;


@FeignClient("product-service")
public interface ProductFeignClient {

	@GetMapping("/findProductByIds")
	@ResponseBody
	public ProductBean findProductByIds(@RequestParam List<Long> id);
	
	@GetMapping("/findProductById")
	@ResponseBody
	public ProductBean findProductById(@RequestParam Long id);
	
	@PostMapping("/productBuy")
    public ResponseEntity<Void> productBuy(@RequestParam int productQuantity, @RequestParam Long productId);
	

}

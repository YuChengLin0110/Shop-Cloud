package com.OrderService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-service")
public interface ProductFeignClient {

	@PostMapping("/getProductQuantityById")
    public int getProductQuantityById(@RequestParam Long id);
}

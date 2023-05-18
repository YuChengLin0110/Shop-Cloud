package com.WebService.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.WebService.Model.ProductBean;

@FeignClient("product-service")
public interface ProductFeignClient {
	
	@GetMapping("/findAllProduct")
	@ResponseBody
	public List<ProductBean> findAllProduct();
	
	@GetMapping("/findProductDetailById")
	@ResponseBody
	public ProductBean findProductDetailById(@RequestParam Long id);
	
	@GetMapping("/findProductById")
	@ResponseBody
	public ProductBean findProductById(@RequestParam Long id);
	
	@PostMapping("/productAdd")
    public ResponseEntity<Void> productAdd(@RequestParam("valName") String name,
                               @RequestParam("valCategory") String category,
                               @RequestParam("valPrice") int price,
                               @RequestParam("valQuantity") int quantity,
                               @RequestParam("valDetail") String detail,
                               @RequestParam("valSpec") String spec,
                               @RequestParam("valImage") MultipartFile file);
	
	@PostMapping("/productUpdate")
    public String productUpdate(@RequestParam Long id,
                                  @RequestParam("valName") String name,
                                  @RequestParam("valCategory") String category,
                                  @RequestParam("valPrice") int price,
                                  @RequestParam("valQuantity") int quantity,
                                  @RequestParam("valDetail") String detail,
                                  @RequestParam("valSpec") String spec,
                                  @RequestParam("valImage") MultipartFile file);
	
	@RequestMapping("/productDelete")
    public String productDelete(@RequestParam Long id);
	
	@PostMapping("/fileUpload")
	public String fileUpload(@RequestParam MultipartFile file);
}

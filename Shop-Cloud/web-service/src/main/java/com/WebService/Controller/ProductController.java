package com.WebService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.WebService.Client.ProductFeignClient;

@Controller
public class ProductController {
	
	@Autowired
	private ProductFeignClient productFeignClient;

	@PostMapping("/productAdd")
    public String productAdd(@RequestParam("valName") String name,
                               @RequestParam("valCategory") String category,
                               @RequestParam("valPrice") int price,
                               @RequestParam("valQuantity") int quantity,
                               @RequestParam("valDetail") String detail,
                               @RequestParam("valSpec") String spec,
                               @RequestParam("valImage") MultipartFile file)  {

    	productFeignClient.productAdd(name,category,price,quantity,detail,spec,file);
    	
        return "redirect:/product";
    }
 
 @PostMapping("/productUpdate/{id}")
    public String productUpdate(@PathVariable Long id,
                                  @RequestParam("valName") String name,
                                  @RequestParam("valCategory") String category,
                                  @RequestParam("valPrice") int price,
                                  @RequestParam("valQuantity") int quantity,
                                  @RequestParam("valDetail") String detail,
                                  @RequestParam("valSpec") String spec,
                                  @RequestParam("valImage") MultipartFile file)  {


        productFeignClient.productUpdate(id,name,category,price,quantity,detail,spec,file);
        
        return "redirect:/product";
    }
 
 @RequestMapping("/productDelete/{id}")
    public String productDelete(@PathVariable Long id) {
    	
    	productFeignClient.productDelete(id);
    	
    	return "redirect:/productAdmin";
    }

}

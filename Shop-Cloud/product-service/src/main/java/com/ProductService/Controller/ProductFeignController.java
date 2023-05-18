package com.ProductService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ProductService.Model.ProductBean;
import com.ProductService.Service.FileService;
import com.ProductService.Service.ProductService;

@Controller
public class ProductFeignController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
	
	
	@GetMapping("/findAllProduct")
	@ResponseBody
	public List<ProductBean> findAllProduct() {
		List<ProductBean> allProducts = productService.findAll();
		

		return allProducts;
	}
	
	@GetMapping("/finProductDetailById")
	@ResponseBody
	public ProductBean productDetail(@RequestParam Long id) {
		ProductBean productBean = productService.findProductById(id);


		return productBean;
	}
	
	@GetMapping("/findProductById")
	@ResponseBody
	public ProductBean findProductById(@RequestParam Long id) {
		ProductBean productBean = productService.findProductById(id);

		return productBean;
	}
	
	@GetMapping("/findProductByIds")
	@ResponseBody
	public List<ProductBean> findProductByIds(@RequestParam List<Long> id) {
		List<ProductBean> productBean = productService.findProductById(id);

		return productBean;
	}
	

    @PostMapping("/productAdd")
    public ResponseEntity<Void> productAdd(@RequestParam("valName") String name,
                               @RequestParam("valCategory") String category,
                               @RequestParam("valPrice") int price,
                               @RequestParam("valQuantity") int quantity,
                               @RequestParam("valDetail") String detail,
                               @RequestParam("valSpec") String spec,
                               @RequestParam("valImage") MultipartFile file)  {


    	String image = fileService.fileUpload(file);
    	
    	
        try {
        	productService.add(name,category,price,quantity,detail,spec,image);
        	return ResponseEntity.ok().build();
        }catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/productUpdate")
    public String productUpdate(@RequestParam Long id,
                                  @RequestParam("valName") String name,
                                  @RequestParam("valCategory") String category,
                                  @RequestParam("valPrice") int price,
                                  @RequestParam("valQuantity") int quantity,
                                  @RequestParam("valDetail") String detail,
                                  @RequestParam("valSpec") String spec,
                                  @RequestParam("valImage") MultipartFile file)  {

    	String image = fileService.fileUpload(file);
        productService.update(name,category,price,quantity,detail,spec,image,id);
        
        return "redirect:/product";
    }
    
    @RequestMapping("/productDelete")
    public String productDelete(@RequestParam Long id) {
    	
    	productService.delete(id);
    	
    	return "redirect:/productAdmin";
    }
    
    
    
    @PostMapping("/productBuy")
    public ResponseEntity<Void> productBuy(@RequestParam int productQuantity, @RequestParam Long productId) {
    	
    	try {
    		productService.productBuy(productQuantity, productId);
    		return ResponseEntity.ok().build();
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    	
		
    }
    
}

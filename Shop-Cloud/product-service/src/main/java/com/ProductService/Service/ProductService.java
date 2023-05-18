package com.ProductService.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.ProductService.DAO.ProductDAO;
import com.ProductService.Model.ProductBean;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("productRedisListTemplate")
	private  RedisTemplate<String, List<ProductBean>> redisListTemplate;
	
	@Autowired
	@Qualifier("productRedisTemplate")
	private  RedisTemplate<String, ProductBean> redisTemplate;
	
	public List<ProductBean> findAll() {
		
		String key = "productList";
		List<ProductBean> productList = new ArrayList<ProductBean>();
		ValueOperations<String, List<ProductBean>> redis = redisListTemplate.opsForValue();
		
		if(redisListTemplate.hasKey(key)) {
			productList = redis.get(key);
		}else {
			productList = productDAO.findAllproduct();
			
			redis.set(key, productList, 5, TimeUnit.MINUTES);
		}
				
		return productList;
	}
	
	public ProductBean findProductById(Long id) {
		
		
		return productDAO.findProductById(id);
	}
	
	public List<ProductBean> findProductById(List<Long> id) {
		return productDAO.findProductById(id);
	}
	
	public void add(String name,
					String category,
					int price,
					int quantity,
					String detail,
					String spec,
					String image) {
		
		productDAO.add(name, category, price, quantity, detail, spec, image);
}
	
	public void update(String name,
			String category,
			int price,
			int quantity,
			String detail,
			String spec,
			String image,
			Long id) {
		
		productDAO.update(name, category, price, quantity, detail, spec, image, id);
}
	
	public void delete(Long id) {
		
		productDAO.delete(id);
	}
	
	public void productBuy(int quantity, Long id) {
		
		productDAO.productBuy(quantity,id);
	}
}

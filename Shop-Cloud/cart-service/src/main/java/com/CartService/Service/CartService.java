package com.CartService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CartService.Client.ProductFeignClient;
import com.CartService.DAO.CartDAO;
import com.CartService.DAO.CartVoMapper;
import com.CartService.Model.CartBean;
import com.CartService.Model.CartVO;

@Service
public class CartService {

	@Autowired 
	CartDAO cartDAO;
	@Autowired
	CartVoMapper cartVoMapper;
	@Autowired 
	ProductFeignClient productFeignClient;
	
	
	public CartBean findCartById(Long id) {
		
		return cartDAO.findCartById(id);
	}
	
	public CartBean findBoughtCartById(Long id) {
		
		return cartDAO.findBoughtCartById(id);
	}
	
	public List<CartBean> findCartById(List<Long> id) {
		
		return cartDAO.findCartById(id);
	}
	
	public List<CartBean> findBoughtCartById(List<Long> id) {
		
		return cartDAO.findBoughtCartById(id);
	}
	
	public List<CartBean> findCartByAccount(String account) {
		
		return cartDAO.findCartByAccount(account);
	}
	
	public List<CartVO> findCartVOByAccount(String account) {
		
		List<CartVO> cartBean = cartVoMapper.findcartVoByAccount(account);
				
		return cartBean;
	}
	
	
	public boolean add(String account,
						Long product_id,
						int quantity) {
		
		
		CartBean cartBean = cartDAO.findCartByProductId(product_id);

		if(cartBean!=null && this.checkQuantity(quantity, product_id)==true) {
			
			quantity = cartBean.getQuantity()+quantity;
			cartDAO.update(quantity, product_id);
			
			return true;
			
		}else if(cartBean==null && this.checkQuantity(quantity, product_id)==true){
			
			cartDAO.add(account, product_id, quantity);
			return true;
		
		}else {	
			return false;
		}
	}
	
	public void update(int quantity,
					   Long id) {
		
		cartDAO.update(quantity, id);
		
	}
	
	public void delete(List<Long> id) {
		
		cartDAO.delete(id);
	}
	 
	public boolean buy(List<Long> id) {
		
		for(Long cid:id) {
			CartBean cartBean = new CartBean();
			cartBean = this.findCartById(cid);
			Long pid = cartBean.getProduct_id();
			int cartQuantity = cartBean.getQuantity();
			
			if(this.checkQuantity(cartQuantity, pid)==true) {
				
				int productQuantity = productFeignClient.findProductById(pid).getQuantity();
				
				productFeignClient.productBuy(productQuantity - cartQuantity,pid);
				cartDAO.buy(cid);
				
			}else {
				return false;
				
			}
			
		}
		return true;
	}
	
	//檢查實際商品數量與購物車商品數量
	public boolean checkQuantity(int cartQuantity, Long product_id) {
		
		int productQuantity = productFeignClient.findProductById(product_id).getQuantity();
		
		if(productQuantity >= cartQuantity) {
			return true;
		}else {
			return false;
		}
	}
}

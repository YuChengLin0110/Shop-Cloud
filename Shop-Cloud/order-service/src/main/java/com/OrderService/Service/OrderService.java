package com.OrderService.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderService.Client.CartFeignClient;
import com.OrderService.Client.ProductFeignClient;
import com.OrderService.DAO.OrderDAO;
import com.OrderService.DAO.OrderVoMapper;
import com.OrderService.Model.OrderAddDataVO;
import com.OrderService.Model.OrderBean;
import com.OrderService.Model.OrderDetailVO;
import com.OrderService.Model.OrderVO;

@Service
public class OrderService {
	
	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	OrderVoMapper orderVoMapper;

	@Autowired
	ProductFeignClient productFeignClient;
	
	@Autowired
	CartFeignClient cartFeignClient;
	
	public List<OrderBean> findOrderByAccount(String account) {

		
		return orderDAO.findOrderByAccount(account);
	}
	
	public List<OrderVO> findOrderVOByAccount(String account){
		return orderVoMapper.findOrderVoByAccount(account);
	}
	
	public List<OrderDetailVO> findOrderDetailVOByOrderNumber(String account, String order_number){
		return orderVoMapper.findOrderDetailVoByOrderNumber(account, order_number);
	}
	
	public OrderVO findOrderByOrderNumber(String order_number) {
		return orderVoMapper.findOrderVoByOrderNumber(order_number);
	}
	
	public Map<String,List<OrderBean>> creatOrderMap(List<OrderBean> orderBeanList){
		Map<String, List<OrderBean>> orderMap = new HashMap<String,List<OrderBean>>();
		
		for(OrderBean orderBean : orderBeanList) {
			
			String key = orderBean.getOrderNumber();
			
			if(!orderMap.containsKey(key)) {
				orderMap.put(key, new ArrayList<OrderBean>());
			}else {
				orderMap.get(key).add(orderBean);
			}
			
		}
		
		return orderMap;
	}
	
	public void addOrder(String account,List<Long> cart_id) {
		
		
		if(cartFeignClient.cartBuy(cart_id)==true) {
		
			String orderNumber = this.createOrderNumber();
			String createDate = this.createDate();
			
			List<OrderAddDataVO> orderAddDataVO = this.prepareOrderAddDataVO(cart_id, account);
			System.out.println(orderAddDataVO);
			for(OrderAddDataVO orderData : orderAddDataVO) {
				int price = orderData.getPrice();
				Long c_id = orderData.getCart_id();
				
				
				this.addOrder(orderNumber, account, price, createDate,c_id, 0);
			}
		}
		
		
	}
	
	public void addOrder(String orderNumber,String account,int productPrice,String createDate,Long cart_id, int status) {
		orderDAO.add(orderNumber, account, productPrice, createDate, cart_id, status);
		
	}
	
	public Map<String,OrderVO> prepareOrderVO(String account){
		Map<String,OrderVO> orderVOMap = new TreeMap<String,OrderVO>();
		
		return orderVOMap; 
	}
	
	public List<OrderAddDataVO> prepareOrderAddDataVO(List<Long> cart_id, String account){

		return orderVoMapper.prepareAddOrderData(cart_id, account);
	}
	
	public void updateOrderStatus(int rtnCode, String orderNumber) {
		orderDAO.updateOrderStatus(rtnCode, orderNumber);
	}
	
	
	public String createOrderNumber(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String type = "OR";
		String number = sdf.format(date);
		return type+number;
	}
	
	public String createDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String createDate = sdf.format(date);
		return createDate;
	}
	
	//檢查實際商品數量與購物車商品數量
	public boolean checkQuqntity(int cartQuantity, Long product_id) {
		int productQuantity = productFeignClient.getProductQuantityById(product_id);
		if(productQuantity >= cartQuantity) {
			return true;
		}else {
			return false;
		}
	}
}

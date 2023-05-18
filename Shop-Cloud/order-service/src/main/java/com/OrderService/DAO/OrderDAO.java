package com.OrderService.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.OrderService.Model.OrderBean;

public interface OrderDAO extends JpaRepository<OrderBean, Long>{
	//order為MySQL關鍵字 需做更改
	
	@Query(value = "SELECT * FROM c_order WHERE account=?1", nativeQuery = true)
	public List<OrderBean> findOrderByAccount(String account);
	
	@Query(value = "SELECT * FROM c_order WHERE order_number = ?1", nativeQuery = true)
		public OrderBean findOrderByOrderNumber(String orderNumber);
	
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO c_order(order_Number,account,product_price,create_date,cart_id,status) VALUES(?1,?2,?3,?4,?5,0)", nativeQuery = true)	
	void add(String orderNumber,String account,int productPrice,String createDate,Long cart_id,int status);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE c_order SET status = ?1 WHERE order_number = ?2")
	void updateOrderStatus(int rtnCode, String orderNumber);

}

package com.WebService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.WebService.Client.OrderFeignClient;
import com.WebService.Model.OrderVO;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class EcpayService {

	@Autowired 
	OrderFeignClient orderFeignClient;
	
	//建立傳入綠界api資料，以綠界sdk提供的form傳入綠界
	public String createEcpayForm(@RequestParam("orderNumber") String orderNumber) {
		
		OrderVO orderVO = orderFeignClient.findOrderByOrdernumber(orderNumber);

		AllInOne all = new AllInOne("");
		
		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(orderVO.getOrder_number());
		obj.setTotalAmount(String.valueOf(orderVO.getPrice()));
		obj.setMerchantTradeDate("2023/05/01 10:05:23");
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		obj.setReturnURL("Domain/ecpayReturn");
		obj.setClientBackURL("Domain/order");
		obj.setNeedExtraPaidInfo("N");
		
		String form = all.aioCheckOut(obj, null);

		return form;
		
	}
}

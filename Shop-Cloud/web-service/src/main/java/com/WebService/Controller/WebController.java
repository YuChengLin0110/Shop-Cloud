package com.WebService.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.WebService.Client.CartFeignClient;
import com.WebService.Client.OrderFeignClient;
import com.WebService.Client.ProductFeignClient;
import com.WebService.Client.UserFeignClient;
import com.WebService.Model.CartVO;
import com.WebService.Model.OrderDetailVO;
import com.WebService.Model.OrderVO;
import com.WebService.Model.ProductBean;
import com.WebService.Model.UserBean;
import com.WebService.Service.UserService;

@Controller
public class WebController {

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private ProductFeignClient productFeignClient;

	@Autowired
	private CartFeignClient cartFeignClient;
	
	@Autowired
	private OrderFeignClient orderFeignClient;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/user")
	public String user(Model model) {

		String username = userService.getUsername();
		UserBean detail = userFeignClient.findByAccount(username);
		model.addAttribute("detail", detail);
		return "user";
	}

	@GetMapping("/userUpdate")
	public String memberUpdate(Model model) {

		String username = userService.getUsername();
		UserBean detail = userFeignClient.findByAccount(username);
		model.addAttribute("detail", detail);
		return "userUpdate";
	}

	@GetMapping("/product")
	public String product(Model model) {

		List<ProductBean> allProduct = productFeignClient.findAllProduct();
		model.addAttribute("allProduct", allProduct);

		return "product";
	}

	@GetMapping("/productAdd")
	public String productAdd() {

		return "productAdd";
	}

	@GetMapping("/productDetail/{id}")
	public String productDetail(@PathVariable Long id, Model model) {
		ProductBean productBean = productFeignClient.findProductById(id);
		model.addAttribute("product", productBean);
		String userName = userService.getUsername();

		model.addAttribute("account", userName);

		return "productDetail";
	}

	@GetMapping("/productUpdate/{id}")
	public String productUpdate(@PathVariable Long id, Model model) {
		ProductBean productBean = productFeignClient.findProductById(id);
		model.addAttribute("product", productBean);

		return "productUpdate";
	}

	@GetMapping("/productAdmin")
	public String productAdmin(Model model) {
		List<ProductBean> productBean = productFeignClient.findAllProduct();
		model.addAttribute("allProduct", productBean);

		return "productAdmin";
	}

	@GetMapping("/cart")
	public String cart(Model model) {

		String userName = userService.getUsername();

		List<CartVO> cartVO = cartFeignClient.findCartVoByAccount(userName);
		model.addAttribute("cartVO", cartVO);

		return "cart";
	}
	
	@GetMapping("/order")
	public String order(Model model) {

		String userName = userService.getUsername();

		List<OrderVO> orderVO = orderFeignClient.findOrderVoByAccount(userName);
		model.addAttribute("orderVO",orderVO);
		
		return "order";
	}
	
	@GetMapping("/orderDetail/{order_number}")
	public String orderDetail(@PathVariable String order_number, Model model) {
		String userName = userService.getUsername();
		List<OrderDetailVO> orderDetailVO = orderFeignClient.findOrderDetailVoByOrderNumber(userName, order_number);
		int status = orderDetailVO.get(0).getStatus();
		model.addAttribute("orderDetailVO",orderDetailVO);
		model.addAttribute("status",status);
		
		return "orderDetail";
	}

	@ModelAttribute("isLogin")
	public boolean isLogin(String username) {

		return userService.isLogin();
	}

	// 設定所有頁面的title變數
	// 以用於左側menu進行邏輯判斷
	// menu.html replace至每個html
	@ModelAttribute("pageTitle")
	public String setTitle(HttpServletRequest request) {
		String path = request.getRequestURI();
		if (path.equals("/cart")) {
			return "購物車";
		} else if (path.startsWith("/cartUpdate")) {
			return "更新購物車";
		} else if (path.equals("/login")) {
			return "會員登入";
		} else if (path.startsWith("/logout")) {
			return "登出";
		} else if (path.equals("/user")) {
			return "會員資料";
		} else if (path.startsWith("/userUpdate")) {
			return "會員資料更新";
		} else if (path.equals("/order")) {
			return "訂單";
		} else if (path.startsWith("/orderDetail")) {
			return "訂單詳情";
		} else if (path.equals("/product")) {
			return "瀏覽商品";
		} else if (path.equals("/productAdd")) {
			return "新增商品";
		} else if (path.equals("/productAdmin")) {
			return "商品管理";
		} else if (path.startsWith("/productDetail")) {
			return "商品詳情";
		} else if (path.startsWith("/productUpdate")) {
			return "商品更新";
		} else if (path.startsWith("/register")) {
			return "會員註冊";
		} else {
			return "Shop";
		}

	}
}

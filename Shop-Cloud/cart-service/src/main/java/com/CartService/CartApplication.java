package com.CartService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//MyBatis用的Mapper
@MapperScan("com.CartService.DAO")
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);

	}

}

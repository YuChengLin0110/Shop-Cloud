package com.ConsulService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsulController {

	 @GetMapping("/")
	    public String test() {
	        return "Hello, world!";
	    }
}
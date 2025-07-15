package com.smart.smartContactManager.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoggedOutUserController {

	Random random = new Random(1000);
	
	@RequestMapping("/forgot")
	public String emailVerifyForm() {
		return "emailForm";
	}
	
	@PostMapping("/sendOTP")
	public String sendOTP(@RequestParam String email) {
		
		System.out.println(email);
	
		
		int otp = random.nextInt(9999);
		System.out.println(otp);
		
		return "verifyOTP";
	}
	
}

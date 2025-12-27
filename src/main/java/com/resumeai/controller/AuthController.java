package com.resumeai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
	@GetMapping("/")
	public String isworking() {
		return "spring boot project started and this is your first mapping";
	}
}

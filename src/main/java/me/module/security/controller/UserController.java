package me.module.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.module.security.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "test";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/login")
	public String login() {
		
		return "login";
	}
	@RequestMapping("/logout")
	public String logout() {
		
		return "login";
	}
	@RequestMapping("/user")
	public String user() {
		
		return "user";
	}
}

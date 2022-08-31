package com.app.ws.ui.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {
	
	@GetMapping
	public String getUser() {
		return "get User was called";
	}
	
	@PostMapping
	public String createUser() {
		return "create User was called";
	}
	
	@PutMapping
	public String updateUser() {
		return "update User was called";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "delete User was called";
	}

}

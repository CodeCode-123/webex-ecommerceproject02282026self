package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.code.api.service.IUserService;
import com.code.api.entity.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	@Autowired
	IUserService userService;
	
	@GetMapping("/")
	public List<Users> getAllUsers() {
		return userService.getAll();
	}
	@GetMapping("/{id}")
	public Users getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	@GetMapping("/search/{emailId}")
	public Users getUserByEmailId(@PathVariable String emailId) {
		return userService.getUserByEmailId(emailId);
	}
	@PostMapping("/create")
	public Users createUsers(@RequestBody Users users) {
		return userService.addUser(users);
	}
	@PutMapping("/edit")
	public Users editUsers(@RequestBody Users users) {
		return userService.updateUser(users);
	}
	@DeleteMapping("/delete/{id}")
	public String deleteUsers(@PathVariable("id") int id) {
		return userService.deleteUser(id);
	}
	
}


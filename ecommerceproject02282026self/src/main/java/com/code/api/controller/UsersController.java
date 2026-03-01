package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.code.api.service.IUserService;
import com.code.api.dto.UsersDTO;
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
	@PatchMapping("/edit/{id}")
	public Users editUsersById(@PathVariable("id") int id, @RequestBody UsersDTO usersDTO) {
		Users dbUsers = userService.getUserById(id);
		if (usersDTO.getFirstName() != null && usersDTO.getFirstName().trim().length() > 0) {
			dbUsers.setFirstName(usersDTO.getFirstName());
		}
		if (usersDTO.getLastName() != null && !usersDTO.getLastName().trim().isBlank()) {
			dbUsers.setLastName(usersDTO.getLastName());
		}
		if (usersDTO.getGender() != null && !usersDTO.getGender().trim().isBlank()) {
			dbUsers.setGender(usersDTO.getGender());
		}
		if (usersDTO.getCountry() != null && !usersDTO.getCountry().trim().isBlank()) {
			dbUsers.setCountry(usersDTO.getCountry());
		}
		if (usersDTO.getLanguages() != null && usersDTO.getLanguages().length > 0) {
			dbUsers.setLanguages(usersDTO.getLanguages());
		}
		return userService.updateUser(dbUsers);
	}
	@DeleteMapping("/delete/{id}")
	public String deleteUsers(@PathVariable("id") int id) {
		return userService.deleteUser(id);
	}
	
}


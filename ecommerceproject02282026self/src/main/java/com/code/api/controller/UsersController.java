package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.code.api.service.IUserService;
import com.code.api.dto.UsersDTO;
import com.code.api.entity.*;
import com.code.api.exception.ResourceNotFoundException;

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
		Optional<Users> users = userService.getUserById(id);
		if (users.isEmpty()) {
			throw new ResourceNotFoundException("Users", "id", String.valueOf(id));
		}
		return userService.getUserById(id).get();
	}
	@GetMapping("/search/{emailId}")
	public Users getUserByEmailId(@PathVariable String emailId) {
		Optional<Users> users = userService.getUserByEmailId(emailId);
		if (users.isEmpty()) {
			throw new ResourceNotFoundException("Users", "emailId", emailId);
		}
		return userService.getUserByEmailId(emailId).get();
	}
	@PostMapping("/login")
	public Users userLogin(@RequestParam("emailId") String emailId, @RequestParam("password") String password) {
		return userService.getUserByEmailId(emailId, password);
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
		Optional<Users> dbUsers = userService.getUserById(id);
		if (dbUsers.isEmpty()) {
			throw new ResourceNotFoundException("Users", "id", String.valueOf(id));
		}
		if (usersDTO.getFirstName() != null && usersDTO.getFirstName().trim().length() > 0) {
			dbUsers.get().setFirstName(usersDTO.getFirstName());
		}
		if (usersDTO.getLastName() != null && !usersDTO.getLastName().trim().isBlank()) {
			dbUsers.get().setLastName(usersDTO.getLastName());
		}
		if (usersDTO.getGender() != null && !usersDTO.getGender().trim().isBlank()) {
			dbUsers.get().setGender(usersDTO.getGender());
		}
		if (usersDTO.getCountry() != null && !usersDTO.getCountry().trim().isBlank()) {
			dbUsers.get().setCountry(usersDTO.getCountry());
		}
		if (usersDTO.getLanguages() != null && usersDTO.getLanguages().length > 0) {
			dbUsers.get().setLanguages(usersDTO.getLanguages());
		}
		return userService.updateUser(dbUsers.get());
	}
	@DeleteMapping("/delete/{id}")
	public String deleteUsers(@PathVariable("id") int id) {
		Optional<Users> users = userService.getUserById(id);
		if (users.isEmpty()) {
			throw new ResourceNotFoundException("Users", "id", String.valueOf(id));
		}
		userService.deleteUserById(id);
		return "Record is deleted successfully";
	}
}


package com.code.api.service;

import java.util.List;
import java.util.Optional;

import com.code.api.entity.Users;

public interface IUserService {
    Users addUser(Users users);
	Users updateUser(Users users);
	void deleteUser(Users users);
	void deleteUserById(int userId);
	Optional<Users> getUserById(int userId);
	Optional<Users> getUserByEmailId(String emailId);	
	Users getUserByEmailId(String emailId, String password);
	List<Users> getAll();
}

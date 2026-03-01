package com.code.api.service;

import java.util.List;
import com.code.api.entity.Users;

public interface IUserService {
	// create the method for the users
	public Users addUser(Users users);

	public Users updateUser(Users users);

	public String deleteUser(Users users);

	public String deleteUser(int userId);

	public Users getUserById(int userId);

	public Users getUserByEmailId(String emailId);
	
	public Users getUserByEmailId(String emailId, String password);

	public List<Users> getAll();
}

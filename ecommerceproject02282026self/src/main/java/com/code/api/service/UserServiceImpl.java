package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.code.api.repository.*;
import com.code.api.entity.Users;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUsersRepository usersRepository;
	
	@Override
	public Users addUser(Users users) {
		// TODO Auto-generated method stub
		return usersRepository.save(users);
	}

	@Override
	public Users updateUser(Users users) {
		// TODO Auto-generated method stub
		return usersRepository.save(users);
	}

	@Override
	public String deleteUser(Users users) {
		// TODO Auto-generated method stub
		usersRepository.delete(users);
		return "Record is deleted successfully";
	}

	@Override
	public String deleteUser(int userId) {
		// TODO Auto-generated method stub
		// find by id
		Optional<Users> usersOptional = usersRepository.findById(userId);
		// check the object if present()
		if (usersOptional.isPresent()) {
			usersRepository.delete(usersOptional.get());
			return "Record is deleted successfully";
		}
		return "Users with id " + userId + " is not found";
	}

	@Override
	public Users getUserById(int userId) {
		// TODO Auto-generated method stub
		// find by id
		Optional<Users> usersOptional = usersRepository.findById(userId);
		Users users = null;
		// check if the users is present()
		if (usersOptional.isPresent()) {
			users = usersOptional.get();
		}
		return users;
	}

	@Override
	public List<Users> getAll() {
		// TODO Auto-generated method stub
		return usersRepository.findAll();
	}

	@Override
	public Users getUserByEmailId(String emailId, String password) {
		// TODO Auto-generated method stub
		// find by email id
		Optional<Users> usersOptional = usersRepository.findByEmailId(emailId);
		Users users = null;
		// check if usersOptional is present()
		if (usersOptional.isPresent()) {
			users = usersOptional.get();
			if (users.getPassword().equals(password)) {
				return users;
			}
		}
		return users;
	}

	@Override
	public Users getUserByEmailId(String emailId) {
		// TODO Auto-generated method stub
		// find by email id
		Optional<Users> usersOptional = usersRepository.findByEmailId(emailId);
		Users users = null;
		// check if usersOptional is present()
		if (usersOptional.isPresent()) {
			users = usersOptional.get();
		}
		return users;
	}
}

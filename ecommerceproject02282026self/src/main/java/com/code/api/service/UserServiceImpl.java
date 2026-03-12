package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
		return usersRepository.save(users);
	}

	@Override
	public Users updateUser(Users users) {
		return usersRepository.save(users);
	}

	@Override
	public void deleteUser(Users users) {
		usersRepository.delete(users);
	}

	@Override
	public void deleteUserById(int userId) {
		usersRepository.deleteById(userId);
	}

	@Override
	public Optional<Users> getUserById(int userId) {
		return usersRepository.findById(userId);
	}

	@Override
	public List<Users> getAll() {
		return usersRepository.findAll();
	}

	@Override
	public Users getUserByEmailId(String emailId, String password) {
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
	public Optional<Users> getUserByEmailId(String emailId) {
		return usersRepository.findByEmailId(emailId);
	}
}

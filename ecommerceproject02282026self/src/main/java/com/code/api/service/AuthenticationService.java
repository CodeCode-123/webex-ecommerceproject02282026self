package com.code.api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.dto.LoginDTO;
import com.code.api.dto.RegisterDTO;
import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;

@Service
@Transactional
public class AuthenticationService {
	private IUsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(
			IUsersRepository usersRepository,
			AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder
			) {
		this.authenticationManager = authenticationManager;
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Users signup(RegisterDTO input) {
		Users customer = new Users();
		customer.setFirstName(input.getFirstName());
		customer.setLastName(input.getLastName());
		customer.setEmailId(input.getEmail());
		customer.setPassword(passwordEncoder.encode(input.getPassword()));
		return usersRepository.save(customer);
	}
	
	public Users authenticate(LoginDTO input) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						input.getEmail(),
						input.getPassword()));
		return usersRepository.findByEmailId(input.getEmail())
				.orElseThrow();
	}

}

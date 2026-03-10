package com.code.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;
import com.code.api.service.UserServiceImpl;

@SpringBootApplication
public class Ecommerceproject02282026selfApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ecommerceproject02282026selfApplication.class, args);
	}
	
	@Bean(name="userService")
	UserServiceImpl getUserService() {
		return new UserServiceImpl();
	}
	
	
	@Bean(name="users")
	@Scope(value="prototype")
	Users getUsers() {
		return new Users();
	}
}

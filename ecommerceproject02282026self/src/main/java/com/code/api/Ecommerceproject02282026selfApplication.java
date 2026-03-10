package com.code.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;
import com.code.api.service.AuthenticationService;
import com.code.api.service.CategoryServiceImpl;
import com.code.api.service.ItemOrderDetailsServiceImpl;
import com.code.api.service.ItemOrderServiceImpl;
import com.code.api.service.JwtService;
import com.code.api.service.UserServiceImpl;

@SpringBootApplication
public class Ecommerceproject02282026selfApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ecommerceproject02282026selfApplication.class, args);
	}
	
	@Bean(name="authenticationService")
	AuthenticationService getAuthenticationService(
			IUsersRepository usersRepository, 
			PasswordEncoder passwordEncoder, 
			AuthenticationManager authenticationManager) {
		return new AuthenticationService(usersRepository, authenticationManager, passwordEncoder);
	}
	@Bean(name="categoryService")
	CategoryServiceImpl getCategoryService() {
		return new CategoryServiceImpl();
	}
	@Bean(name="itemOrderService")
	ItemOrderServiceImpl getItemOrderService() {
		return new ItemOrderServiceImpl();
	}
	@Bean(name="orderDetailsService")
	ItemOrderDetailsServiceImpl getItemOrderDetailsService() {
		return new ItemOrderDetailsServiceImpl();
	}
	@Bean(name="userService")
	UserServiceImpl getUserService() {
		return new UserServiceImpl();
	}
	@Bean(name="jwtService")
	JwtService getJwtService() {
		return new JwtService();
	}
	
	@Bean(name="category")
	@Scope(value="prototype")
	Category getCategory() {
		return new Category();
	}
	@Bean(name="item")
	@Scope(value="prototype")
	Item getItem() {
		return new Item();
	}
	@Bean(name="itemOrder")
	@Scope(value="prototype")
	ItemOrder getItemOrder() {
		return new ItemOrder();
	}
	@Bean(name="itemOrderDetails")
	@Scope(value="prototype")
	ItemOrderDetails getItemOrderDetails() {
		return new ItemOrderDetails();
	}
	@Bean(name="users")
	@Scope(value="prototype")
	Users getUsers() {
		return new Users();
	}
}

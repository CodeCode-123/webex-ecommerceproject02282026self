package com.code.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.code.api.dto.CategoryDTO;
import com.code.api.dto.ItemDTO;
import com.code.api.dto.LoginDTO;
import com.code.api.dto.RegisterDTO;
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
import com.fasterxml.jackson.databind.ObjectMapper;

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
	@Bean(name="itemOrderDetailsService")
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
	@Bean(name="registerDTO")
	@Scope(value="prototype")
	RegisterDTO getRegisterDTO() {
		return new RegisterDTO();
	}
	@Bean(name="loginDTO")
	@Scope(value="prototype")
	LoginDTO getLoginDTO() {
		return new LoginDTO();
	}
	@Bean(name="itemDTO")
	@Scope(value="prototype")
	ItemDTO getItemDTO() {
		return new ItemDTO();
	}
	@Bean(name="objectMapper")
	@Scope(value="prototype")
	ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	@Bean(name="categoryDTO")
	@Scope(value="prototype")
	CategoryDTO getCategoryDTO() {
		return new CategoryDTO();
	}
}

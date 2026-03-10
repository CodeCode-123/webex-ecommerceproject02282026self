package com.code.api;

import static org.mockito.Mockito.when;


import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;
import com.code.api.service.UserServiceImpl;

@SpringBootTest(classes = Ecommerceproject02282026selfApplication.class)
public class UserServiceTest {
	@Autowired
	ApplicationContext applicationContext;
	@Mock
	private IUsersRepository usersRepository;
	@InjectMocks
	private UserServiceImpl userService;
	@Autowired
	Users userOne;
	@Autowired
	Users userTwo;
	
	public UserServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		// set user one
		userOne.setId(1);
		userOne.setEmailId("admin@abc.com");
	    userOne.setFirstName("admin");
		userOne.setLastName("admin");
		userOne.setPassword("1234");
		// set user two
		userTwo.setId(2);
		userTwo.setEmailId("customer@abc.com");
	    userTwo.setFirstName("customer");
		userTwo.setLastName("customer");
		userTwo.setPassword("1234");
	}
	
	@Test
	void testGetUserById() {
		when(usersRepository.findById(1)).thenReturn(Optional.of(userOne));
		assertSame(userOne, userService.getUserById(1));
		assertEquals(userOne.getEmailId(), userService.getUserById(1).getEmailId());
	}
	
	
	@Test
	void testGetUserByEmailId() {
		when(usersRepository.findByEmailId("admin@abc.com")).thenReturn(Optional.of(userOne));
		assertSame(userOne, userService.getUserByEmailId("admin@abc.com"));
		assertEquals(userOne.getId(), userService.getUserByEmailId("admin@abc.com").getId());
	}
	
	@Test
	void testGetAll() {
		when(usersRepository.findAll()).thenReturn(List.of(userOne, userTwo));
		assertEquals(List.of(userOne, userTwo), userService.getAll());
	}
}

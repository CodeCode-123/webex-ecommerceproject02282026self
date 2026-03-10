package com.code.api;

import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.code.api.repository.IUsersRepository;
import com.code.api.service.IUserService;

@SpringBootTest
public class CategoryServiceTest {
	@Mock
	private IUsersRepository usersRepository;
	@InjectMocks
	private IUserService userService;
	
	public CategoryServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void testGetCategoryById() {
		
	}

}

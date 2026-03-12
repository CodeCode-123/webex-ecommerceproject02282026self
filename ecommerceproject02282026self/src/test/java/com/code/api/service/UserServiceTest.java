package com.code.api.service;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;
import com.code.api.service.UserServiceImpl;

@SpringBootTest(classes = Ecommerceproject02282026selfApplication.class)
public class UserServiceTest {
	@Mock
	private IUsersRepository usersRepository;
	@InjectMocks
	private UserServiceImpl userService;
	@Autowired
	private Users userOneToSave;
	@Autowired
	private Users userOneSaved;
	@Autowired
	private Users userOneUpdated;
	@Autowired
	private Users userTwoToSave;
	@Autowired
	private Users userTwoSaved;
	
	public UserServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		// set user one ToSave
		userOneToSave.setEmailId("admin@abc.com");
	    userOneToSave.setFirstName("Admin");
		userOneToSave.setLastName("Admin");
		userOneToSave.setPassword("123456");
		// set user one Saved
		userOneSaved.setId(1);
		userOneSaved.setEmailId("admin@abc.com");
	    userOneSaved.setFirstName("Admin");
		userOneSaved.setLastName("Admin");
		userOneSaved.setPassword("123456");
		
		// set user two ToSave
		userTwoToSave.setEmailId("customer@abc.com");
		userTwoToSave.setFirstName("Customer");
		userTwoToSave.setLastName("Customer");
		userTwoToSave.setPassword("123456");
		// set user two Saved
		userTwoSaved.setId(2);
		userTwoSaved.setEmailId("customer@abc.com");
		userTwoSaved.setFirstName("Customer");
		userTwoSaved.setLastName("Customer");
		userTwoSaved.setPassword("123456");
	}
	
	@Test
	void testGetUserById() {
		when(usersRepository.findById(1)).thenReturn(Optional.of(userOneSaved));
		assertSame(userOneSaved, userService.getUserById(1).get());
		assertEquals(userOneSaved.getEmailId(), userService.getUserById(1).get().getEmailId());
		verify(usersRepository, times(2)).findById(anyInt());
	}
	
	@Test
	void testGetUserByEmailId() {
		when(usersRepository.findByEmailId("admin@abc.com")).thenReturn(Optional.of(userOneSaved));
		assertSame(userOneSaved, userService.getUserByEmailId("admin@abc.com").get());
		assertEquals(userOneSaved.getId(), userService.getUserByEmailId("admin@abc.com").get().getId());
		verify(usersRepository, times(2)).findByEmailId(anyString());
	}
	
	@Test
	void testGetAll() {
		when(usersRepository.findAll()).thenReturn(List.of(userOneSaved, userTwoSaved));
		assertEquals(List.of(userOneSaved, userTwoSaved), userService.getAll());
		verify(usersRepository, times(1)).findAll();
	}
	
	@Test
	void testAddUser() {
		when(usersRepository.save(userOneToSave)).thenReturn(userOneSaved);
		when(usersRepository.save(userTwoToSave)).thenReturn(userTwoSaved);
		assertSame(userOneSaved, userService.addUser(userOneToSave));
		assertSame(userTwoSaved, userService.addUser(userTwoToSave));
		ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
		verify(usersRepository, times(2)).save(captor.capture());
	}
	
	@Test
	void testUpdateUser() {
		// set user one Updated
		userOneUpdated.setId(1);
		userOneUpdated.setEmailId("admin1@abc.com");
		userOneUpdated.setFirstName("Admin1");
		userOneUpdated.setLastName("Admin1");
		userOneUpdated.setPassword("123456");
		when(usersRepository.save(userOneUpdated)).thenReturn(userOneUpdated);
		assertSame(userOneUpdated, userService.updateUser(userOneUpdated));
		ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
		verify(usersRepository, times(1)).save(captor.capture());
	}
	
	@Test
	void testDeleteUser() {
		doNothing().when(usersRepository).delete(userOneSaved);
		doNothing().when(usersRepository).delete(userTwoSaved);
		userService.deleteUser(userOneSaved);
		userService.deleteUser(userTwoSaved);
		ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
		verify(usersRepository, times(2)).delete(captor.capture());
	}
	
	@Test
	void testDeleteUserById() {
		doNothing().when(usersRepository).deleteById(1);
		doNothing().when(usersRepository).deleteById(2);
		userService.deleteUserById(1);
		userService.deleteUserById(2);
		verify(usersRepository, times(2)).deleteById(anyInt());
	}
}

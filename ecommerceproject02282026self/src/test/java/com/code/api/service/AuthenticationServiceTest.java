package com.code.api.service;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.dto.LoginDTO;
import com.code.api.dto.RegisterDTO;
import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;

@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
public class AuthenticationServiceTest {
	@Mock
	private IUsersRepository usersRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private AuthenticationManager authenticationManager;
	@InjectMocks
	private AuthenticationService authenticationService;
	@Autowired
	private RegisterDTO registerDTOAdmin;
	@Autowired
	private RegisterDTO registerDTOCustomer;
	@Autowired
	private LoginDTO loginDTOAdmin;
	@Autowired
	private LoginDTO loginDTOCustomer;
	@Autowired
	private Users adminUsersToSave;
	@Autowired
	private Users adminUsersSaved;
	@Autowired
	private Users customerUsersToSave;
	@Autowired
	private Users customerUsersSaved;
	public AuthenticationServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		// set admin registerDTO
		registerDTOAdmin.setFirstName("Admin");
		registerDTOAdmin.setLastName("Admin");
		registerDTOAdmin.setEmail("admin@abc.com");
		registerDTOAdmin.setPassword("123456");
		// set admin userToSave
		adminUsersToSave.setFirstName(registerDTOAdmin.getFirstName());
		adminUsersToSave.setLastName(registerDTOAdmin.getLastName());
		adminUsersToSave.setEmailId(registerDTOAdmin.getEmail());
		adminUsersToSave.setPassword(passwordEncoder.encode(registerDTOAdmin.getPassword()));
		// set admin userSaved
		adminUsersSaved.setId(1);
		adminUsersSaved.setFirstName(adminUsersToSave.getFirstName());
		adminUsersSaved.setLastName(adminUsersToSave.getLastName());
		adminUsersSaved.setEmailId(adminUsersToSave.getEmailId());
		adminUsersSaved.setPassword(adminUsersToSave.getPassword());

		// set customer registerDTO
		registerDTOCustomer.setFirstName("Customer");
		registerDTOCustomer.setLastName("Customer");
		registerDTOCustomer.setEmail("customer@abc.com");
		registerDTOCustomer.setPassword("123456");
		// set customer userToSave
		customerUsersToSave.setFirstName(registerDTOCustomer.getFirstName());
		customerUsersToSave.setLastName(registerDTOCustomer.getLastName());
		customerUsersToSave.setEmailId(registerDTOCustomer.getEmail());
		customerUsersToSave.setPassword(passwordEncoder.encode(registerDTOCustomer.getPassword()));
		// set customer userSaved
		customerUsersSaved.setId(2);
		customerUsersSaved.setFirstName(customerUsersToSave.getFirstName());
		customerUsersSaved.setLastName(customerUsersToSave.getLastName());
		customerUsersSaved.setEmailId(customerUsersToSave.getEmailId());
		customerUsersSaved.setPassword(customerUsersToSave.getPassword());

		// set admin loginDTO
		loginDTOAdmin.setEmail("admin@abc.com");
		loginDTOAdmin.setPassword("123456");
		// set customer loginDTO
		loginDTOCustomer.setEmail("customer@abc.com");
		loginDTOCustomer.setPassword("123456");
	}
	
//	@Test
//	void signup() {
//		when(passwordEncoder.encode("123456")).thenReturn("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
//		when(usersRepository.save(adminUsersToSave)).thenReturn(adminUsersSaved);
//		when(usersRepository.save(customerUsersToSave)).thenReturn(customerUsersSaved);
//		assertSame(adminUsersSaved, authenticationService.signup(registerDTOAdmin));
//		assertSame(customerUsersSaved, authenticationService.signup(registerDTOCustomer));
//		ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
//		verify(usersRepository, times(2)).save(captor.capture());
//	}
	@Test
	void authenticate() {
		when(usersRepository.findByEmailId(loginDTOAdmin.getEmail())).thenReturn(Optional.of(adminUsersSaved));
		when(usersRepository.findByEmailId(loginDTOCustomer.getEmail())).thenReturn(Optional.of(customerUsersSaved));
		assertSame(adminUsersSaved, authenticationService.authenticate(loginDTOAdmin));
		assertSame(customerUsersSaved, authenticationService.authenticate(loginDTOCustomer));
		verify(usersRepository, times(2)).findByEmailId(anyString());
	}
}

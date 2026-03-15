package com.code.api.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.dto.LoginDTO;
import com.code.api.dto.RegisterDTO;
import com.code.api.entity.Users;
import com.code.api.service.AuthenticationService;
import com.code.api.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
@Transactional
public class AuthenticationControllerTest {
	private static MockHttpServletRequest request;
	@PersistenceContext
	private EntityManager entityManager;
	@Mock
	private JwtService jwtService;
	@Mock
	private AuthenticationService authenticationServiceMock;
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private RegisterDTO registerDTO;	
	@Autowired
	private LoginDTO loginDTO;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Value("${SQL_ADD_USERS_ONE}")
	private String sqlAddUsersOne;
	@Value("${SQL_DELETE_USERS}")
	private String sqlDeleteUsers;
	@Value("${SQL_RESET_USERS}")
	private String sqlResetUsers;
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
		request.setParameter("firstName", "John");
		request.setParameter("lastName", "Doe");
		request.setParameter("email", "john.doe@abc.com");
		request.setParameter("password", "123456");
	}
	@BeforeEach
	public void setupDatabase() {
		jdbc.update(sqlAddUsersOne);
	}
	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute(sqlDeleteUsers);
		jdbc.execute(sqlResetUsers);
	}

	@Test
	@DisplayName("Test Registration")
	void testRegistrationHttpRequest() throws Exception {
		registerDTO.setFirstName("John");
		registerDTO.setLastName("Doe");
		registerDTO.setEmail("john.doe@abc.com");
		registerDTO.setPassword("123456");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")				
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.id", is(2)))
		.andExpect(jsonPath("$.firstName", is("John")))
		.andExpect(jsonPath("$.lastName", is("Doe")))
		.andExpect(jsonPath("$.emailId", is("john.doe@abc.com")));
	}
	@Test
	@DisplayName("Test Registration bad request")
	void testRegistrationBadRequestHttpRequest() throws Exception {
		// set an invalid first name
		registerDTO.setFirstName("");
		// set an invalid last name
		registerDTO.setLastName(" ");
		// set an invalid email
		registerDTO.setEmail("john.doe");
		// set an invalid password
		registerDTO.setPassword("1234");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")				
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDTO)))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.firstName", is("First name is required")))
		.andExpect(jsonPath("$.lastName", is("Last name is required")))
		.andExpect(jsonPath("$.email", is("Invalid email format")))
		.andExpect(jsonPath("$.password", is("Password must be between 6 to 12 characters")));
	}
}

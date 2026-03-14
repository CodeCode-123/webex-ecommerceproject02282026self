package com.code.api.controller;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.dto.UsersDTO;
import com.code.api.entity.Users;
import com.code.api.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Lob;
import jakarta.persistence.PersistenceContext;


@TestPropertySource("/application-test.properties")
@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UsersControllerTest {
	private static MockHttpServletRequest request;
	@PersistenceContext
	private EntityManager entityManager;
	@Mock
	private UserServiceImpl userServiceMock;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private Users userOne;
	@Autowired
	private Users userTwo;
	@Autowired
	private UsersDTO usersDTO;
	@Autowired
	private MockMvc mockMvc;
	private static MediaType mediaType=MediaType.APPLICATION_JSON;
	@Value("${SQL_ADD_USERS_ONE}")
	private String sqlAddUsersOne;
	@Value("${SQL_DELETE_USERS}")
	private String sqlDeleteUsers;
	@Value("${SQL_RESET_USERS}")
	private String sqlResetUsers;
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();		
	}
	@BeforeEach
	public void setupDatabase() {
		jdbc.execute(sqlAddUsersOne);		
	}
	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute(sqlDeleteUsers);
		jdbc.execute(sqlResetUsers);
	}
	@Test
	@DisplayName("Get all users")
	@WithMockUser(username="testuser", roles={"USER"})
	public void getAllUsersHttpRequest() throws Exception{
		userTwo.setFirstName("John");
		userTwo.setLastName("Doe");
		userTwo.setGender("male");
		userTwo.setLanguages(new String[] {"Java", "Python"});
		userTwo.setEmailId("john.doe@abc.com");
		userTwo.setCountry("France");
		userTwo.setPassword("123456");
		entityManager.persist(userTwo);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].firstName", is("John")))
		.andExpect(jsonPath("$[1].lastName", is("Doe")))
		.andExpect(jsonPath("$[1].emailId", is("john.doe@abc.com")));
	}
	@Test
	@DisplayName("Get user by id")
	@WithMockUser(username="testuser", roles={"USER"})
	public void getUserByIdHttpRequest() throws Exception{
		userTwo.setFirstName("John");
		userTwo.setLastName("Doe");
		userTwo.setGender("male");
		userTwo.setLanguages(new String[] {"Java", "Python"});
		userTwo.setEmailId("john.doe@abc.com");
		userTwo.setCountry("France");
		userTwo.setPassword("123456");
		entityManager.persist(userTwo);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.firstName", is("test")))
		.andExpect(jsonPath("$.lastName", is("test")))
		.andExpect(jsonPath("$.emailId", is("test@abc.com")));
	}
	@Test
	@DisplayName("Get user by emailId")
	@WithMockUser(username="testuser", roles={"USER"})
	public void getUserByEmailIdHttpRequest() throws Exception{
		userTwo.setFirstName("John");
		userTwo.setLastName("Doe");
		userTwo.setGender("male");
		userTwo.setLanguages(new String[] {"Java", "Python"});
		userTwo.setEmailId("john.doe@abc.com");
		userTwo.setCountry("France");
		userTwo.setPassword("123456");
		entityManager.persist(userTwo);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/search/{emailId}", "john.doe@abc.com")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.id", is(2)))
		.andExpect(jsonPath("$.firstName", is("John")))
		.andExpect(jsonPath("$.lastName", is("Doe")))
		.andExpect(jsonPath("$.emailId", is("john.doe@abc.com")));
	}
	@Test
	@DisplayName("Login with email and password")
	@WithMockUser(username="testuser", roles={"USER"})
	public void loginHttpRequest() throws Exception{
		userTwo.setFirstName("John");
		userTwo.setLastName("Doe");
		userTwo.setGender("male");
		userTwo.setLanguages(new String[] {"Java", "Python"});
		userTwo.setEmailId("john.doe@abc.com");
		userTwo.setCountry("France");
		userTwo.setPassword("123456");
		entityManager.persist(userTwo);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.param("emailId", "test@abc.com")
				.param("password", "123456"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.firstName", is("test")))
		.andExpect(jsonPath("$.lastName", is("test")))
		.andExpect(jsonPath("$.emailId", is("test@abc.com")));
	}
	@Test
	@DisplayName("Create a user")
	@WithMockUser(username="testuser", roles={"USER"})
	public void createUsersHttpRequest() throws Exception{
		userTwo.setFirstName("John");
		userTwo.setLastName("Doe");
		userTwo.setGender("male");
		userTwo.setLanguages(new String[] {"Java", "Python"});
		userTwo.setEmailId("john.doe@abc.com");
		userTwo.setCountry("France");
		userTwo.setPassword("123456");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/create")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(userTwo)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.id", is(2)))
		.andExpect(jsonPath("$.firstName", is("John")))
		.andExpect(jsonPath("$.lastName", is("Doe")))
		.andExpect(jsonPath("$.emailId", is("john.doe@abc.com")));
	}
	@Test
	@DisplayName("Edit a user")
	@WithMockUser(username="testuser", roles={"USER"})
	public void editUsersHttpRequest() throws Exception{
		userOne.setId(1);
		userOne.setFirstName("test1");
		userOne.setLastName("test1");
		userOne.setGender("male");
		userOne.setLanguages(new String[] {"Java", "JavaScript", "TypeScript", "C++", "Python"});
		userOne.setEmailId("test1@abc.com");
		userOne.setCountry("France");
		userOne.setPassword("123456");
		mockMvc.perform(MockMvcRequestBuilders.put("/api/users/edit")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(userOne)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.firstName", is("test1")))
		.andExpect(jsonPath("$.emailId", is("test1@abc.com")));
	}
	@Test
	@DisplayName("Edit a user by id")
	@WithMockUser(username="testuser", roles={"USER"})
	public void editUsersByIdHttpRequest() throws Exception{
		String[] langs = new String[] {"Java", "JavaScript", "TypeScript", "C++", "Python"};
		usersDTO.setLanguages(langs);
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/edit/{id}", 1)
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(usersDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.firstName", is("test")))
		.andExpect(jsonPath("$.languages.length()", is(5)))
		.andExpect(jsonPath("$.languages[0]", is(langs[0])))
		.andExpect(jsonPath("$.languages[4]", is(langs[4])));
	}
	@Test
	@DisplayName("delete a user by id")
	@WithMockUser(username="testuser", roles={"USER"})
	public void deleteUsersByIdHttpRequest() throws Exception{
		String[] langs = new String[] {"Java", "JavaScript", "TypeScript", "C++", "Python"};
		usersDTO.setLanguages(langs);
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/{id}", 1)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))
		.andExpect(content().string("Record is deleted successfully"));
	}
}

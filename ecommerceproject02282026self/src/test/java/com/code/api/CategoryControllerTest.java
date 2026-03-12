package com.code.api;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.context.support.WithMockUser;

import com.code.api.entity.Category;
import com.code.api.repository.ICategoryRepository;
import com.code.api.service.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
@Transactional
public class CategoryControllerTest {
	private static MockHttpServletRequest request;
	@PersistenceContext
	private EntityManager entityManager;
	@Mock
	private CategoryServiceImpl categoryServiceMock;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ICategoryRepository categoryRepository;
	@Autowired
	private CategoryServiceImpl categoryService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private Category category;
	public static final MediaType mediaType = MediaType.APPLICATION_JSON;
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
		request.setParameter("categoryName", "Burger");
		request.setParameter("categoryDesc", "Best Value");
	}
	@BeforeEach
	public void setupDatabase() {
		jdbc.execute("INSERT INTO category(category_name, category_desc) VALUES('Pizza', 'Any Pizza, Any Toppings')");	
	}
	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute("DELETE FROM category");
		jdbc.execute("ALTER TABLE category ALTER COLUMN category_id RESTART WITH 1");
	}
	@Test
	@WithMockUser(username="testuser", roles={"USER"})
	public void getCategoryHttpRequest() throws Exception {
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		entityManager.persist(category);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$", hasSize(2)));
	}
	@Test
	@WithMockUser(username="testuser", roles={"USER"})
	public void getCategoryByIdHttpRequest() throws Exception {
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		entityManager.persist(category);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/2")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.categoryId", is(2)))
		.andExpect(jsonPath("$.categoryName", is("Burger")))
		.andExpect(jsonPath("$.categoryDesc", is("Best Value")));
	}

	
	

}

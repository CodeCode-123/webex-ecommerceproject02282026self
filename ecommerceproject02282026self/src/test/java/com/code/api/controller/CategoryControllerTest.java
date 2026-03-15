package com.code.api.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.context.support.WithMockUser;

import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.dto.CategoryDTO;
import com.code.api.entity.Category;
import com.code.api.service.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private Category category;
	@Autowired
	private CategoryDTO categoryDTO;
	public static final MediaType mediaType = MediaType.APPLICATION_JSON;
	@Value("${SQL_ADD_CATEGORY_ONE}")
	private String sqlAddCategoryOne;
	@Value("${SQL_DELETE_CATEGORY}")
	private String sqlDeleteCategory;
	@Value("${SQL_RESET_CATEGORY}")
	private String sqlResetCategory;
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
		request.setParameter("categoryName", "Burger");
		request.setParameter("categoryDesc", "Best Value");
	}
	@BeforeEach
	public void setupDatabase() {
		jdbc.execute(sqlAddCategoryOne);	
	}
	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute(sqlDeleteCategory);
		jdbc.execute(sqlResetCategory);
	}
	@Test
	@DisplayName("Get all categories")
	@WithMockUser(username="testuser", roles={"USER"})
	public void getAllCategoriesHttpRequest() throws Exception {
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		entityManager.persist(category);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].categoryId", is(1)))
		.andExpect(jsonPath("$[0].categoryName", is("Pizza")))
		.andExpect(jsonPath("$[0].categoryDesc", is("Any Pizza, Any Toppings")))
		.andExpect(jsonPath("$[1].categoryId", is(2)))
		.andExpect(jsonPath("$[1].categoryName", is("Burger")))
		.andExpect(jsonPath("$[1].categoryDesc", is("Best Value")));
	}
	@Test
	@DisplayName("Get category by id")
	@WithMockUser(username="testuser", roles={"USER"})
	public void getCategoryByIdHttpRequest() throws Exception {
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		entityManager.persist(category);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/{id}", 2)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.categoryId", is(2)))
		.andExpect(jsonPath("$.categoryName", is("Burger")))
		.andExpect(jsonPath("$.categoryDesc", is("Best Value")));
	}
	@Test
	@DisplayName("Get category by id not found")
	@WithMockUser(username="testuser", roles={"USER"})
	public void getCategoryByIdNotFoundHttpRequest() throws Exception {
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		entityManager.persist(category);
		entityManager.flush();
		// set a category id not found in the database
		int categoryId = 3;
		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/{id}", categoryId)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.errorCode", is("404 NOT_FOUND")))
		.andExpect(jsonPath("$.errorMessage", is("Category was not found with the given input data categoryId: " + categoryId)));
	}
	@Test
	@DisplayName("Get category by searching name like")
	@WithMockUser(username="testuser", roles={"USER"})
	public void getCategoryBySearchNameHttpRequest() throws Exception {
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		entityManager.persist(category);
		entityManager.flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/search/{catename}", "B")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$").isArray()) 
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].categoryId", is(2)))
		.andExpect(jsonPath("$[0].categoryName", is("Burger")))
		.andExpect(jsonPath("$[0].categoryDesc", is("Best Value")));
	}
	@Test
	@DisplayName("Get category by searching name like not found")
	@WithMockUser(username="testuser", roles={"USER"})
	public void getCategoryBySearchNameNotFoundHttpRequest() throws Exception {
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		entityManager.persist(category);
		entityManager.flush();
		// set a category name like not found in the database
		String categoryNameLike = "D";
		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/search/{catename}", categoryNameLike)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.errorCode", is("404 NOT_FOUND")))
		.andExpect(jsonPath("$.errorMessage", is("Category was not found with the given input data categoryName: " + categoryNameLike)));
	}
	@Test
	@DisplayName("Create a category")
	@WithMockUser(username="testuser", roles={"USER"})
	public void createCategoryHttpRequest() throws Exception {
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/category/create")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(category)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.categoryId", is(2)))
		.andExpect(jsonPath("$.categoryName", is("Burger")))
		.andExpect(jsonPath("$.categoryDesc", is("Best Value")));
	}
	@Test
	@DisplayName("Edit a category")
	@WithMockUser(username="testuser", roles={"USER"})
	public void editCategoryHttpRequest() throws Exception {
		category.setCategoryId(1);
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		mockMvc.perform(MockMvcRequestBuilders.put("/api/category/edit")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(category)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.categoryId", is(1)))
		.andExpect(jsonPath("$.categoryName", is("Burger")))
		.andExpect(jsonPath("$.categoryDesc", is("Best Value")));
	}
	@Test
	@DisplayName("Edit a category by id")
	@WithMockUser(username="testuser", roles={"USER"})
	public void editCategoryByIdHttpRequest() throws Exception {
		categoryDTO.setCategoryDesc("Double Cheese Pizza");
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/category/edit/{id}", 1)
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.categoryId", is(1)))
		.andExpect(jsonPath("$.categoryName", is("Pizza")))
		.andExpect(jsonPath("$.categoryDesc", is("Double Cheese Pizza")));
	}
	@Test
	@DisplayName("Edit a category by id not found")
	@WithMockUser(username="testuser", roles={"USER"})
	public void editCategoryByIdNotFoundHttpRequest() throws Exception {
		categoryDTO.setCategoryDesc("Double Cheese Pizza");
		// set the category id not found in the database
		int categoryId = 4;
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/category/edit/{id}", categoryId)
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDTO)))
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.errorCode", is("404 NOT_FOUND")))
		.andExpect(jsonPath("$.errorMessage", is("Category was not found with the given input data categoryId: " + categoryId)));
	}
	@Test
	@DisplayName("Edit a category by id bad request")
	@WithMockUser(username="testuser", roles={"USER"})
	public void editCategoryByIdBadRequestHttpRequest() throws Exception {
		// set the category name to be empty, violating the categoryDTO validation
		categoryDTO.setCategoryName("");
		int categoryId = 1;
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/category/edit/{id}", categoryId)
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDTO)))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.categoryName", is("Category name should be at least 1 character")));
	}
	@Test
	@DisplayName("Delete a category by id")
	@WithMockUser(username="testuser", roles={"USER"})
	public void deleteategoryByIdHttpRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/delete/{id}", 1)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))
		.andExpect(content().string("Record is deleted successfully"));
	}
	@Test
	@DisplayName("Delete a category by id not existed")
	@WithMockUser(username="testuser", roles={"USER"})
	public void deleteategoryByIdNotExistedHttpRequest() throws Exception {
		// set the category id not found in the database
		int categoryId = 10;
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/delete/{id}", categoryId)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.errorCode", is("404 NOT_FOUND")))
		.andExpect(jsonPath("$.errorMessage", is("Category was not found with the given input data categoryId: " + categoryId)));
	}
}

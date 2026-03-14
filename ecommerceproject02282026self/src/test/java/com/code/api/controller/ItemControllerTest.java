package com.code.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.http.MediaType;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.dto.ItemDTO;
import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.service.CategoryServiceImpl;
import com.code.api.service.ItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.contentOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.ResponseCache;
import java.sql.PreparedStatement;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
@AutoConfigureMockMvc
@Transactional
public class ItemControllerTest {
	private static MockHttpServletRequest request;
	@PersistenceContext
	private EntityManager entityManager;
	@Mock
	private ItemServiceImpl itemService;
	@Autowired
	private CategoryServiceImpl categoryService;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private Item itemOne;
	@Autowired
	private Item itemTwo;
	@Autowired
	private ItemDTO itemDTO;
	@Autowired
	private Category category;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	private static final MediaType mediaType=MediaType.APPLICATION_JSON;
	@Value("${SQL_ADD_CATEGORY_ONE}")
	private String sqlAddCategoryOne;
	@Value("${SQL_ADD_CATEGORY_TWO}")
	private String sqlAddCategoryTwo;
	@Value("${SQL_DELETE_CATEGORY}")
	private String sqlDeleteCategory;
	@Value("${SQL_RESET_CATEGORY}")
	private String sqlResetCategory;
	@Value("${SQL_DELETE_ITEM}")
	private String sqlDeleteItem;
	@Value("${SQL_RESET_ITEM}")
	private String sqlResetItem;
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
		request.setParameter("itemName", "Big Mac");
		request.setParameter("itemPrice", "8");
	}
	@BeforeEach
	public void setupDatabase() {
		// insert two categories
		jdbc.execute(sqlAddCategoryOne);
		jdbc.execute(sqlAddCategoryTwo);
		// set an item and save it to the database
		String sqlAddItem = "INSERT INTO item(item_name, item_price, category_id) VALUES(?,?,?)";
		jdbc.update(sqlAddItem, "Cheese Pizza", 10, 1);
		jdbc.update(sqlAddItem, "Cheese Burger", 6, 2);
	}
	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute(sqlDeleteItem);
		jdbc.execute(sqlResetItem);
		jdbc.execute(sqlDeleteCategory);
		jdbc.execute(sqlResetCategory);
	}
	@Test
	@DisplayName("Get all items")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void getAllItemsHttpRequest() throws Exception{
		// perform get method
		mockMvc.perform(MockMvcRequestBuilders.get("/api/item/")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].itemId", is(1)))
		.andExpect(jsonPath("$[0].itemName", is("Cheese Pizza")))
		.andExpect(jsonPath("$[0].itemPrice", is(10)))
		.andExpect(jsonPath("$[1].itemId", is(2)))
		.andExpect(jsonPath("$[1].itemName", is("Cheese Burger")))
		.andExpect(jsonPath("$[1].itemPrice", is(6)));
	}
	@Test
	@DisplayName("Get item by id")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void getItemByIdHttpRequest() throws Exception{
		// perform get method
		mockMvc.perform(MockMvcRequestBuilders.get("/api/item/{id}", 2)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.itemId", is(2)))
		.andExpect(jsonPath("$.itemName", is("Cheese Burger")))
		.andExpect(jsonPath("$.itemPrice", is(6)));
	}
	@Test
	@DisplayName("Create item")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void createItemHttpRequest() throws Exception{
		// set another item
		category.setCategoryId(2);
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Value");
		itemTwo.setItemName("Double Cheese Burger");
		itemTwo.setItemPrice(8);
		itemTwo.setCategory(category);
		// perform post method
		mockMvc.perform(MockMvcRequestBuilders.post("/api/item/create")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemTwo)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.itemId", is(3)))
		.andExpect(jsonPath("$.itemName", is("Double Cheese Burger")))
		.andExpect(jsonPath("$.itemPrice", is(8)));
	}
	@Test
	@DisplayName("Edit item")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void editItemHttpRequest() throws Exception{
		// set edited item
		category.setCategoryId(1);
		category.setCategoryName("Pizza");
		category.setCategoryDesc("Any Pizza, Any Toppings");
		itemTwo.setItemId(1);
		itemTwo.setItemName("Double Cheese Pizza");
		itemTwo.setItemPrice(15);
		itemTwo.setCategory(category);
		// perform put method
		mockMvc.perform(MockMvcRequestBuilders.put("/api/item/edit")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemTwo)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.itemId", is(1)))
		.andExpect(jsonPath("$.itemName", is("Double Cheese Pizza")))
		.andExpect(jsonPath("$.itemPrice", is(15)));
	}
	@Test
	@DisplayName("Edit item by id")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void editItemByIdHttpRequest() throws Exception{
		// set edited item
		itemDTO.setItemName("Double Cheese Pizza");
		itemDTO.setItemPrice(15);
		// perform patch method
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/item/edit/{id}", 1)
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.itemId", is(1)))
		.andExpect(jsonPath("$.itemName", is("Double Cheese Pizza")))
		.andExpect(jsonPath("$.itemPrice", is(15)));
	}
	@Test
	@DisplayName("Delete item by id")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void deleteItemByIdHttpRequest() throws Exception{
		// perform delete method
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/item/delete/{id}", 1)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))
		.andExpect(content().string("Record is deleted successfully"));
	}
}

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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.dto.OrderDetailsDTO;
import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.service.ItemOrderDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
@Transactional
public class OrderDetailsControllerTest {
	private static MockHttpServletRequest request;
	@PersistenceContext
	private EntityManager entityManager;
	@Mock
	private ItemOrderDetailsServiceImpl itemOrderDetailsServiceMock;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private Category categoryOne;
	@Autowired
	private Item itemThree;
	@Autowired
	private OrderDetailsDTO orderDetailsDTO;
	@Autowired
	private MockMvc mockMvc;
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
	@Value("${SQL_DELETE_ITEM_ORDER_DETAILS}")
	private String sqlDeleteItemOrderDetails;
	@Value("${SQL_RESET_ITEM_ORDER_DETAILS}")
	private String sqlResetItemOrderDetails;
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
	}
	@BeforeEach
	public void setupDatabase() {
		// add Category
		jdbc.execute(sqlAddCategoryOne);
		jdbc.execute(sqlAddCategoryTwo);
		// add Item
		String sqlAddItem = "INSERT INTO item(item_name, item_price, category_id) VALUES(?,?,?)";
		jdbc.update(sqlAddItem, "Cheese Pizza", 10, 1);
		jdbc.update(sqlAddItem, "Cheese Burger", 6, 2);
		// add ItemOrderDetails
		String sqlAddItemOrderDetails = "INSERT INTO item_order_details(item_id, qty, item_value) VALUES(?,?,?)";
		jdbc.update(sqlAddItemOrderDetails, 1, 2, 20);
		jdbc.update(sqlAddItemOrderDetails, 2, 3, 18);
	}
	@AfterEach
	public void setupAfterTransactional() {
		jdbc.execute(sqlDeleteItemOrderDetails);
		jdbc.execute(sqlResetItemOrderDetails);
		jdbc.execute(sqlDeleteItem);
		jdbc.execute(sqlResetItem);
		jdbc.execute(sqlDeleteCategory);
		jdbc.execute(sqlResetCategory);
	}
	private Item createItemThree() {
		categoryOne.setCategoryId(1);
		categoryOne.setCategoryName("Pizza");
		categoryOne.setCategoryDesc("Any Pizza, Any Toppings");
		itemThree.setItemName("Double Cheese Pizza");
		itemThree.setItemPrice(15);
		itemThree.setCategory(categoryOne);
		entityManager.persist(itemThree);
		return itemThree;
	}
	private OrderDetailsDTO createItemOrderDetailsDTO() {
		itemThree = createItemThree();
		int qty = 3;
		orderDetailsDTO.setItem(itemThree);
		orderDetailsDTO.setQty(qty);
		return orderDetailsDTO;
	}
	private OrderDetailsDTO updateItemOrderDetailsDTO() {
		itemThree = createItemThree();
		int qty = 3;
		orderDetailsDTO.setItemOrderDetailsId(2);
		orderDetailsDTO.setItem(itemThree);
		orderDetailsDTO.setQty(qty);
		return orderDetailsDTO;
	}
	private OrderDetailsDTO updateByIdItemOrderDetailsDTO() {
		int qty = 5;
		orderDetailsDTO.setQty(qty);
		return orderDetailsDTO;
	}
	
	@Test
	@DisplayName("Get all item order details")
	@WithMockUser(username="testUser", roles={"USER"})
	public void getAllItemOrderDetailsHttpRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/orderdetails/")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].item.itemId", is(1)))
		.andExpect(jsonPath("$[0].qty", is(2)))
		.andExpect(jsonPath("$[0].itemValue", is(20.0)))
		.andExpect(jsonPath("$[1].item.itemId", is(2)))
		.andExpect(jsonPath("$[1].qty", is(3)))
		.andExpect(jsonPath("$[1].itemValue", is(18.0)));
	}
	@Test
	@DisplayName("Get item order details by id")
	@WithMockUser(username="testUser", roles={"USER"})
	public void getItemOrderDetailsByIdHttpRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/orderdetails/{id}", 2)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.item.itemId", is(2)))
		.andExpect(jsonPath("$.qty", is(3)))
		.andExpect(jsonPath("$.itemValue", is(18.0)));
	}
	@Test
	@DisplayName("Create item order details")
	@WithMockUser(username="testUser", roles={"USER"})
	public void createItemOrderDetailsHttpRequest() throws Exception {
		orderDetailsDTO = createItemOrderDetailsDTO();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/orderdetails/create")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(orderDetailsDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.item.itemName", is("Double Cheese Pizza")))
		.andExpect(jsonPath("$.qty", is(3)))
		.andExpect(jsonPath("$.itemValue", is(45.0)));
	}
	@Test
	@DisplayName("Edit item order details")
	@WithMockUser(username="testUser", roles={"USER"})
	public void editItemOrderDetailsHttpRequest() throws Exception {
		orderDetailsDTO = updateItemOrderDetailsDTO();
		mockMvc.perform(MockMvcRequestBuilders.put("/api/orderdetails/edit")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(orderDetailsDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.itemOrderDetailsId", is(2)))
		.andExpect(jsonPath("$.item.itemName", is("Double Cheese Pizza")))
		.andExpect(jsonPath("$.qty", is(3)))
		.andExpect(jsonPath("$.itemValue", is(45.0)));
	}
	@Test
	@DisplayName("Edit item order details by id")
	@WithMockUser(username="testUser", roles={"USER"})
	public void editItemOrderDetailsByIdHttpRequest() throws Exception {
		orderDetailsDTO = updateByIdItemOrderDetailsDTO();
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/orderdetails/edit/{id}", 1)
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(orderDetailsDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.itemOrderDetailsId", is(1)))
		.andExpect(jsonPath("$.item.itemName", is("Cheese Pizza")))
		.andExpect(jsonPath("$.qty", is(5)))
		.andExpect(jsonPath("$.itemValue", is(50.0)));
	}
	@Test
	@DisplayName("Delete item order details by id")
	@WithMockUser(username="testUser", roles={"USER"})
	public void deleteItemOrderDetailsByIdHttpRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/orderdetails/delete/{id}", 2)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))
		.andExpect(content().string("Record is deleted successfully"));
	}
}

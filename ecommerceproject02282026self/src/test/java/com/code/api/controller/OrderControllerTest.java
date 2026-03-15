package com.code.api.controller;

import java.util.List;

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
import com.code.api.dto.OrderRequestDTO;
import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Users;
import com.code.api.service.ItemOrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
	private static MockHttpServletRequest request;
	@PersistenceContext
	private EntityManager entityManager;
	@Mock
	private ItemOrderServiceImpl orderServiceMock;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Users users;
	@Autowired
	private ItemOrder itemOrderOne;
	@Autowired
	private ItemOrder itemOrderTwo;
	@Autowired
	private OrderRequestDTO orderRequestDTO;
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
	@Value("${SQL_DELETE_ITEM_ORDER_DETAILS}")
	private String sqlDeleteItemOrderDetails;
	@Value("${SQL_RESET_ITEM_ORDER_DETAILS}")
	private String sqlResetItemOrderDetails;
	@Value("${SQL_DELETE_ITEM_ORDER}")
	private String sqlDeleteItemOrder;
	@Value("${SQL_RESET_ITEM_ORDER}")
	private String sqlResetItemOrder;
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
		// add Category
		jdbc.execute(sqlAddCategoryOne);
		jdbc.execute(sqlAddCategoryTwo);
		// add Item
		String sqlAddItem = "INSERT INTO item(item_name, item_price, category_id) VALUES(?,?,?)";
		jdbc.update(sqlAddItem, "Cheese Pizza", 10, 1);
		jdbc.update(sqlAddItem, "Cheese Burger", 6, 2);
		jdbc.update(sqlAddItem, "Double Cheese Burger", 8, 2);
		// add ItemOrderDetails
		String sqlAddItemOrderDetails = "INSERT INTO item_order_details(item_id, qty, item_value) VALUES(?,?,?)";
		jdbc.update(sqlAddItemOrderDetails, 1, 2, 20);
		jdbc.update(sqlAddItemOrderDetails, 2, 3, 18);
		jdbc.update(sqlAddItemOrderDetails, 3, 5, 40);
	}

	@AfterEach
	public void setupAfterTransactional() {
		jdbc.execute(sqlDeleteItemOrder);
		jdbc.execute(sqlResetItemOrder);
		jdbc.execute(sqlDeleteItemOrderDetails);
		jdbc.execute(sqlResetItemOrderDetails);
		jdbc.execute(sqlDeleteItem);
		jdbc.execute(sqlResetItem);
		jdbc.execute(sqlDeleteCategory);
		jdbc.execute(sqlResetCategory);
		jdbc.execute(sqlDeleteUsers);
		jdbc.execute(sqlResetUsers);
	}
	private Users createUsers() {
		users.setFirstName("John");
		users.setLastName("Doe");
		users.setCountry("France");
		users.setLanguages(new String[] {"Java", "C", "C#"});
		users.setGender("Male");
		users.setEmailId("john.doe@abc.com");
		users.setPassword("123456");
		users.setRole("Admin");
		entityManager.persist(users);
		return users;
	}
	private ItemOrder createItemOrderOne() {
		users = createUsers();
		itemOrderOne.setUsers(users);
	    ItemOrderDetails itemOrderDetailsOne = entityManager.find(ItemOrderDetails.class, 1);
		itemOrderOne.setItemOrderDetailsList(List.of(itemOrderDetailsOne));
		double totalAmount = itemOrderDetailsOne.getItem().getItemPrice() * itemOrderDetailsOne.getQty();
		itemOrderOne.setTotalAmount(totalAmount);
		entityManager.persist(itemOrderOne);
		return itemOrderOne;
	}
	private ItemOrder createItemOrderTwo() {
		itemOrderTwo.setUsers(users);
		ItemOrderDetails itemOrderDetailsTwo = entityManager.find(ItemOrderDetails.class, 2);
		itemOrderTwo.setItemOrderDetailsList(List.of(itemOrderDetailsTwo));
		double totalAmount = itemOrderDetailsTwo.getItem().getItemPrice() * itemOrderDetailsTwo.getQty();
		itemOrderTwo.setTotalAmount(totalAmount);
		entityManager.persist(itemOrderTwo);
		return itemOrderTwo;
	}
	@Test
	@DisplayName("Get all orders")
	@WithMockUser(username="testUser", roles={"USER"})
	public void getAllOrdersHttpRequest() throws Exception {
		createItemOrderOne();
		createItemOrderTwo();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/")
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].itemOrderDetailsList[0].item.itemName", is("Cheese Pizza")))
		.andExpect(jsonPath("$[0].itemOrderDetailsList[0].item.itemPrice", is(10)))
		.andExpect(jsonPath("$[0].totalAmount", is(20.0)))
		.andExpect(jsonPath("$[1].itemOrderDetailsList[0].item.itemName", is("Cheese Burger")))
		.andExpect(jsonPath("$[1].itemOrderDetailsList[0].item.itemPrice", is(6)))
		.andExpect(jsonPath("$[1].totalAmount", is(18.0)));
	}
	@Test
	@DisplayName("Get orders by id")
	@WithMockUser(username="testUser", roles={"USER"})
	public void getOrdersByIdHttpRequest() throws Exception {
		createItemOrderOne();
		createItemOrderTwo();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{id}", 2)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.itemOrderDetailsList[0].item.itemName", is("Cheese Burger")))
		.andExpect(jsonPath("$.itemOrderDetailsList[0].item.itemPrice", is(6)))
		.andExpect(jsonPath("$.totalAmount", is(18.0)));
	}
	@Test
	@DisplayName("Get orders by id not found")
	@WithMockUser(username="testUser", roles={"USER"})
	public void getOrdersByIdNotFoundHttpRequest() throws Exception {
		createItemOrderOne();
		createItemOrderTwo();
		// set an itemOrderId not found in the database
		int itemOrderId = 0;
		mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{id}", itemOrderId)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.errorCode", is("404 NOT_FOUND")))
		.andExpect(jsonPath("$.errorMessage", 
				is("ItemOrder was not found with the given input data itemOrderId: "
						+ itemOrderId)));
	}
	@Test
	@DisplayName("Create orders")
	@WithMockUser(username="testUser", roles={"USER"})
	public void createOrdersHttpRequest() throws Exception {
		users = createUsers();
		orderRequestDTO.setUsers(users);
	    ItemOrderDetails itemOrderDetailsOne = entityManager.find(ItemOrderDetails.class, 1);
	    ItemOrderDetails itemOrderDetailsTwo = entityManager.find(ItemOrderDetails.class, 2);
		orderRequestDTO.setItemOrderDetailsList(List.of(itemOrderDetailsOne, itemOrderDetailsTwo));
		double totalAmount = itemOrderDetailsOne.getItem().getItemPrice() * itemOrderDetailsOne.getQty();
		orderRequestDTO.setTotalAmount(totalAmount);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/placeorder")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(orderRequestDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.itemOrderDetailsList[0].item.itemName", is("Cheese Pizza")))
		.andExpect(jsonPath("$.itemOrderDetailsList[0].item.itemPrice", is(10)))
		.andExpect(jsonPath("$.itemOrderDetailsList[0].qty", is(2)))
		.andExpect(jsonPath("$.itemOrderDetailsList[0].itemValue", is(20.0)))
		.andExpect(jsonPath("$.itemOrderDetailsList[1].item.itemName", is("Cheese Burger")))
		.andExpect(jsonPath("$.itemOrderDetailsList[1].item.itemPrice", is(6)))
		.andExpect(jsonPath("$.itemOrderDetailsList[1].qty", is(3)))
		.andExpect(jsonPath("$.itemOrderDetailsList[1].itemValue", is(18.0)))
		.andExpect(jsonPath("$.users.id", is(1)))
		.andExpect(jsonPath("$.totalAmount", is(38.0)));	
	}
	@Test
	@DisplayName("Create orders bad request")
	@WithMockUser(username="testUser", roles={"USER"})
	public void createOrdersBadRequestHttpRequest() throws Exception {
		users = createUsers();
		orderRequestDTO.setUsers(users);
	    ItemOrderDetails itemOrderDetailsOne = entityManager.find(ItemOrderDetails.class, 1);
	    ItemOrderDetails itemOrderDetailsTwo = entityManager.find(ItemOrderDetails.class, 2);
		orderRequestDTO.setItemOrderDetailsList(List.of(itemOrderDetailsOne, itemOrderDetailsTwo));
		// set an invalid totalAmount
		orderRequestDTO.setTotalAmount(-10);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/placeorder")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(orderRequestDTO)))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.totalAmount", is("Total amount should not be negative")));
	}
	@Test
	@DisplayName("Delete orders by id")
	@WithMockUser(username="testUser", roles={"USER"})
	public void deleteOrdersByIdHttpRequest() throws Exception {
		createItemOrderOne();
		createItemOrderTwo();
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/delete/{id}", 2)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))
		.andExpect(content().string("Record is deleted successfully"));
	}
	@Test
	@DisplayName("Delete orders by id not found")
	@WithMockUser(username="testUser", roles={"USER"})
	public void deleteOrdersByIdNotFoundHttpRequest() throws Exception {
		createItemOrderOne();
		createItemOrderTwo();
		// set an itemOrderId not found in the database
		int itemOrderId = 0;
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/delete/{id}", itemOrderId)
				.with(SecurityMockMvcRequestPostProcessors.jwt()))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.errorCode", is("404 NOT_FOUND")))
		.andExpect(jsonPath("$.errorMessage", 
				is("ItemOrder was not found with the given input data itemOrderId: "
						+ itemOrderId)));
	}
}

package com.code.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
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
import com.code.api.dto.OrderRequestDTO;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
@AutoConfigureMockMvc
@Transactional
public class PaymentControllerTest {
	private static MockHttpServletRequest request;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired 
	private JdbcTemplate jdbc;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private Users usersOne;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOne;
	@Autowired
	private ItemOrderDetails itemOrderDetailsTwo;
	@Autowired
	private OrderRequestDTO orderRequestDTO;
	public static final MediaType mediaType=MediaType.APPLICATION_JSON;
	@Value("${SQL_ADD_CATEGORY_ONE}")
	private String sqlAddCategoryOne;
	@Value("${SQL_ADD_CATEGORY_TWO}")
	private String sqlAddCategoryTwo;
	@Value("${SQL_ADD_USERS_ONE}")
	private String sqlAddUsersOne;
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
		// add Users
		jdbc.execute(sqlAddUsersOne);
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
	private OrderRequestDTO setupOrderRequestDTO() {
		// retrieve users one
		usersOne = entityManager.find(Users.class, 1);
		// retrieve item order details one
		itemOrderDetailsOne = entityManager.find(ItemOrderDetails.class, 1);
		itemOrderDetailsTwo = entityManager.find(ItemOrderDetails.class, 2);
		// set up a order request DTO with users and ItemOrderDetailsList
		orderRequestDTO.setUsers(usersOne);
		orderRequestDTO.setItemOrderDetailsList(List.of(itemOrderDetailsOne, itemOrderDetailsTwo));
		return orderRequestDTO;
	}
	private Map<String, String> setupConfirmOrderRequestBody() {
		Map<String, String> data = new HashMap<>();
		data.put("razorpay_order_id", "order_SRJjf0IIfBpI8i");
		data.put("razorpay_payment_id", "txn_1773537508412");
		data.put("razorpay_signature", "b6d478d7942772dd1cf8c627e428fa6af353348b64dc6a85797463a509a67700");	
		return data;
	}
	
	@Test
	@DisplayName("Create a Razorpay order")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void createRazorpayOrder() throws Exception {
		orderRequestDTO = setupOrderRequestDTO();
		// perform post method
		mockMvc.perform(MockMvcRequestBuilders.post("/api/payment/createorder")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(orderRequestDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/plain;charset=UTF-8"));
	}
	@Test
	@DisplayName("Confirm the Razorpay order")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void confirmRazorpayOrder() throws Exception {
	    Map<String, String> data = setupConfirmOrderRequestBody();
		// perform post method
		mockMvc.perform(MockMvcRequestBuilders.post("/api/payment/confirmpayment")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(data)))
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))
		.andExpect(content().string("Payment Successful"));
	}
	@Test
	@DisplayName("Confirm the Razorpay order invalid signature")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void confirmRazorpayOrderInvalidSignature() throws Exception {
	    Map<String, String> data = setupConfirmOrderRequestBody();
	    // set an invalid signature
	    data.put("razorpay_signature", "InvalidSignature");
		// perform post method
		mockMvc.perform(MockMvcRequestBuilders.post("/api/payment/confirmpayment")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(data)))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))
		.andExpect(content().string("Invalid Signature"));
	}
	@Test
	@DisplayName("Confirm the Razorpay order verification failed")
	@WithMockUser(username="testUser", roles= {"USER"})
	public void confirmRazorpayOrderVerificationFailed() throws Exception {
	    Map<String, String> data = new HashMap<>();
	    // set up the request body with a wrong key "order_id" (should be "razorpay_order_id")
	    data.put("order_id", "order_SRJjf0IIfBpI8i");
		data.put("razorpay_payment_id", "txn_1773537508412");
		data.put("razorpay_signature", "b6d478d7942772dd1cf8c627e428fa6af353348b64dc6a85797463a509a67700");	
		// perform post method
		mockMvc.perform(MockMvcRequestBuilders.post("/api/payment/confirmpayment")
				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(data)))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))
		.andExpect(content().string("Verification Failed"));
	}
}

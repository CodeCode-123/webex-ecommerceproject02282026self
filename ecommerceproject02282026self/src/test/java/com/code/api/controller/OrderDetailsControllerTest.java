package com.code.api.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.transaction.annotation.Transactional;

import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.dto.OrderDetailsDTO;
import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.service.ItemOrderDetailsServiceImpl;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	private Category category;
	@Autowired
	private Item item;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOne;
	@Autowired
	private ItemOrderDetails itemOrderDetailsTwo;
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
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
	}
	@BeforeEach
	public void setupDatabase() {
		// add Category
		jdbc.execute(sqlAddCategoryOne);
		jdbc.execute(sqlAddCategoryTwo);
		// add ItemOrderDetails
		String sqlAddItem = "INSERT INTO item(item_name, item_price, category_id) VALUES(?,?,?)";
		jdbc.update(sqlAddItem, "Cheese Pizza", 10, 1);
		jdbc.update(sqlAddItem, "Cheese Burger", 6, 2);
	}
	@AfterEach
	public void setupAfterTransactional() {
		jdbc.execute(sqlDeleteItem);
		jdbc.execute(sqlResetItem);
		jdbc.execute(sqlDeleteCategory);
		jdbc.execute(sqlResetCategory);
	}
	@Test
	public void test1() {
		
	}

}

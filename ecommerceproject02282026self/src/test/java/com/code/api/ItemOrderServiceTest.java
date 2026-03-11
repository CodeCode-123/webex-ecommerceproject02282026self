package com.code.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Users;
import com.code.api.repository.IOrdersRepository;
import com.code.api.service.ItemOrderServiceImpl;

@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
public class ItemOrderServiceTest {
	@Mock
	IOrdersRepository orderRepository;
	@InjectMocks
	ItemOrderServiceImpl orderService;
	@Autowired
	Users userOne;
	@Autowired
	Users userTwo;
	@Autowired
    Category category;
	@Autowired
	Item item;
	@Autowired
	ItemOrderDetails itemOrderDetailsOne;
	@Autowired
	ItemOrderDetails itemOrderDetailsTwo;
	@Autowired
	ItemOrderDetails itemOrderDetailsThree;
	@Autowired
	ItemOrder itemOrderOne;
	@Autowired
	ItemOrder itemOrderTwo;
	
	public ItemOrderServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		// set user one
		userOne.setId(1);
		userOne.setEmailId("admin@abc.com");
		userOne.setFirstName("admin");
		userOne.setLastName("admin");
		userOne.setPassword("1234");
		// set user two
		userTwo.setId(2);
		userTwo.setEmailId("customer@abc.com");
		userTwo.setFirstName("customer");
		userTwo.setLastName("customer");
		userTwo.setPassword("1234");
		
		// set category one
		category.setCategoryId(1);
		category.setCategoryName("Pizza");
		category.setCategoryDesc("Any Pizza, any toppings");
		// set item one
		item.setItemId(1);
		item.setCategory(category);
		item.setItemName("Cheese Pizza");
		item.setItemPrice(10);
		// set item order details one
		int qty = 2;
		itemOrderDetailsOne.setItemOrderDetailsId(1);
		itemOrderDetailsOne.setItem(item);
		itemOrderDetailsOne.setQty(2);
		itemOrderDetailsOne.setItemValue(item.getItemPrice() * qty);
		
		// set category two
		category.setCategoryId(2);
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Price");
		// set item two
		item.setItemId(2);
		item.setCategory(category);
		item.setItemName("Big Mac");
		item.setItemPrice(8);
		// set item order details two
		qty = 3;
		itemOrderDetailsTwo.setItemOrderDetailsId(2);
		itemOrderDetailsTwo.setItem(item);
		itemOrderDetailsTwo.setQty(qty);
		itemOrderDetailsTwo.setItemValue(item.getItemPrice() * qty);
		
		// set item three
		item.setItemId(3);
		item.setCategory(category);
		item.setItemName("Cheese Burger");
		item.setItemPrice(6);
		// set item order details three
		qty = 5;
		itemOrderDetailsThree.setItemOrderDetailsId(3);
		itemOrderDetailsThree.setItem(item);
		itemOrderDetailsThree.setQty(qty);
		itemOrderDetailsThree.setItemValue(item.getItemPrice() * qty);
		
		// set item order one
		itemOrderOne.setOrderId(1);
		itemOrderOne.setUsers(userOne);
		itemOrderOne.setItemOrderDetailsList(List.of(itemOrderDetailsOne));
		itemOrderOne.setTotalAmount(itemOrderDetailsOne.getItemValue());
		LocalDateTime nowTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = nowTime.format(formatter);
		itemOrderOne.setOrderDate(formattedDate);
		
		// set item order two
		itemOrderTwo.setOrderId(2);
		itemOrderTwo.setUsers(userTwo);
		itemOrderTwo.setItemOrderDetailsList(List.of(itemOrderDetailsTwo, itemOrderDetailsThree));
		itemOrderTwo.setTotalAmount(itemOrderDetailsTwo.getItemValue()+itemOrderDetailsThree.getItemValue());
		nowTime = LocalDateTime.now();
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		formattedDate = nowTime.format(formatter);
		itemOrderTwo.setOrderDate(formattedDate);
	}

	@Test
	void getOrderById() {
		when(orderRepository.findById(1)).thenReturn(Optional.of(itemOrderOne));
		when(orderRepository.findById(2)).thenReturn(Optional.of(itemOrderTwo));
		assertSame(itemOrderOne, orderService.getById(1).get());
		assertSame(itemOrderTwo, orderService.getById(2).get());
		assertEquals(List.of(itemOrderDetailsOne), orderService.getById(1).get().getItemOrderDetailsList());
		assertEquals(List.of(itemOrderDetailsTwo, itemOrderDetailsThree), orderService.getById(2).get().getItemOrderDetailsList());
		verify(orderRepository, times(4)).findById(anyInt());
	}
	@Test
	void getOrderAndOrderDetailsById() {
		when(orderRepository.findOrderAndItemOrderDetailsById(1)).thenReturn(Optional.of(itemOrderOne));
		when(orderRepository.findOrderAndItemOrderDetailsById(2)).thenReturn(Optional.of(itemOrderTwo));
		assertSame(itemOrderOne, orderService.getOrderAndItemOrderDetailsById(1).get());
		assertSame(itemOrderTwo, orderService.getOrderAndItemOrderDetailsById(2).get());
		assertEquals(List.of(itemOrderDetailsOne), orderService.getOrderAndItemOrderDetailsById(1).get().getItemOrderDetailsList());
		assertEquals(List.of(itemOrderDetailsTwo, itemOrderDetailsThree), orderService.getOrderAndItemOrderDetailsById(2).get().getItemOrderDetailsList());
		verify(orderRepository, times(4)).findOrderAndItemOrderDetailsById(anyInt());
	}
	@Test
	void getAllOrders() {
		when(orderRepository.findAll()).thenReturn(List.of(itemOrderOne, itemOrderTwo));
		assertEquals(List.of(itemOrderOne, itemOrderTwo), orderService.getAll());
		verify(orderRepository, times(1)).findAll();
	}
}

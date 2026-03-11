package com.code.api;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.repository.IOrderDetailsRepository;
import com.code.api.service.ItemOrderDetailsServiceImpl;
import java.util.*;

@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
public class ItermOrderDetailsServiceTest {
	@Mock
	IOrderDetailsRepository orderDetailsRepository;
	@InjectMocks
	ItemOrderDetailsServiceImpl orderDetailsService;
	@Autowired
    Category category;
	@Autowired
	Item item;
	@Autowired
	ItemOrderDetails itemOrderDetailsOne;
	@Autowired
	ItemOrderDetails itemOrderDetailsTwo;
	public ItermOrderDetailsServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	@BeforeEach
	public void beforeEach() {
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
	}
	@Test
	void getOrderDetailsById() {
		when(orderDetailsRepository.findById(1)).thenReturn(Optional.of(itemOrderDetailsOne));
		when(orderDetailsRepository.findById(2)).thenReturn(Optional.of(itemOrderDetailsTwo));
		assertSame(itemOrderDetailsOne, orderDetailsService.getById(1));
		assertSame(itemOrderDetailsTwo, orderDetailsService.getById(2));
		verify(orderDetailsRepository, times(1)).findById(1);
		verify(orderDetailsRepository, times(1)).findById(2);
	}
	@Test
	void getOrderDetailsAndItemById() {
		when(orderDetailsRepository.findItemOrderDetailsAndItemById(1)).thenReturn(Optional.of(itemOrderDetailsOne));
		when(orderDetailsRepository.findItemOrderDetailsAndItemById(2)).thenReturn(Optional.of(itemOrderDetailsTwo));
		assertSame(itemOrderDetailsOne, orderDetailsService.getOrderDetailsAndItemById(1).get());
		assertSame(itemOrderDetailsTwo, orderDetailsService.getOrderDetailsAndItemById(2).get());
		assertSame(itemOrderDetailsOne.getItem(), orderDetailsService.getOrderDetailsAndItemById(1).get().getItem());
		assertSame(itemOrderDetailsTwo.getItem(), orderDetailsService.getOrderDetailsAndItemById(2).get().getItem());
		verify(orderDetailsRepository, times(2)).findItemOrderDetailsAndItemById(1);
		verify(orderDetailsRepository, times(2)).findItemOrderDetailsAndItemById(2);
	}
	@Test
	void getAllOrders() {
		when(orderDetailsRepository.findAll()).thenReturn(List.of(itemOrderDetailsOne, itemOrderDetailsTwo));
		assertEquals(List.of(itemOrderDetailsOne, itemOrderDetailsTwo), orderDetailsService.getAll());
		verify(orderDetailsRepository, times(1)).findAll();
	}

}

package com.code.api.service;

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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.code.api.Ecommerceproject02282026selfApplication;
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
	private IOrdersRepository orderRepository;
	@InjectMocks
	private ItemOrderServiceImpl orderService;
	@Autowired
	private Users userOne;
	@Autowired
	private Users userTwo;
	@Autowired
    private Category category;
	@Autowired
	private Item item;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOne;
	@Autowired
	private ItemOrderDetails itemOrderDetailsTwo;
	@Autowired
	private ItemOrderDetails itemOrderDetailsThree;
	@Autowired
	private ItemOrderDetails itemOrderDetailsFour;
	@Autowired
	private ItemOrder itemOrderOneToSave;
	@Autowired
	private ItemOrder itemOrderOneSaved;
	@Autowired
	private ItemOrder itemOrderTwoToSave;
	@Autowired
	private ItemOrder itemOrderTwoSaved;
	@Autowired
	private ItemOrder itemOrderTwoUpdated;
	
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
		
		// set item four
		item.setItemId(4);
		item.setCategory(category);
		item.setItemName("Double Cheese Burger");
		item.setItemPrice(8);
		
		// set item order details four
		qty = 6;
		itemOrderDetailsFour.setItemOrderDetailsId(4);
		itemOrderDetailsFour.setItem(item);
		itemOrderDetailsFour.setQty(qty);
		itemOrderDetailsFour.setItemValue(item.getItemPrice() * qty);
		
		// set item order one ToSave
		itemOrderOneToSave.setUsers(userOne);
		itemOrderOneToSave.setItemOrderDetailsList(List.of(itemOrderDetailsOne));
		itemOrderOneToSave.setTotalAmount(itemOrderDetailsOne.getItemValue());
		LocalDateTime nowTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = nowTime.format(formatter);
		itemOrderOneToSave.setOrderDate(formattedDate);
		// set item order one Saved
		itemOrderOneSaved.setOrderId(1);
		itemOrderOneSaved.setUsers(userOne);
		itemOrderOneSaved.setItemOrderDetailsList(List.of(itemOrderDetailsOne));
		itemOrderOneSaved.setTotalAmount(itemOrderDetailsOne.getItemValue());
		itemOrderOneToSave.setOrderDate(formattedDate);
		
		// set item order two ToSave
		itemOrderTwoToSave.setUsers(userTwo);
		itemOrderTwoToSave.setItemOrderDetailsList(List.of(itemOrderDetailsTwo, itemOrderDetailsThree));
		itemOrderTwoToSave.setTotalAmount(itemOrderDetailsTwo.getItemValue() + itemOrderDetailsThree.getItemValue());
		nowTime = LocalDateTime.now();
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		formattedDate = nowTime.format(formatter);
		itemOrderTwoToSave.setOrderDate(formattedDate);
		// set item order two Saved
		itemOrderTwoSaved.setOrderId(2);
		itemOrderTwoSaved.setUsers(userTwo);
		itemOrderTwoSaved.setItemOrderDetailsList(List.of(itemOrderDetailsTwo, itemOrderDetailsThree));
		itemOrderTwoSaved.setTotalAmount(itemOrderDetailsTwo.getItemValue() + itemOrderDetailsThree.getItemValue());
		itemOrderTwoSaved.setOrderDate(formattedDate);
	}

	@Test
	void getOrderById() {
		when(orderRepository.findById(1)).thenReturn(Optional.of(itemOrderOneSaved));
		when(orderRepository.findById(2)).thenReturn(Optional.of(itemOrderTwoSaved));
		assertSame(itemOrderOneSaved, orderService.getById(1).get());
		assertSame(itemOrderTwoSaved, orderService.getById(2).get());
		assertEquals(List.of(itemOrderDetailsOne), orderService.getById(1).get().getItemOrderDetailsList());
		assertEquals(List.of(itemOrderDetailsTwo, itemOrderDetailsThree), orderService.getById(2).get().getItemOrderDetailsList());
		verify(orderRepository, times(4)).findById(anyInt());
	}
	@Test
	void getOrderAndOrderDetailsById() {
		when(orderRepository.findOrderAndItemOrderDetailsById(1)).thenReturn(Optional.of(itemOrderOneSaved));
		when(orderRepository.findOrderAndItemOrderDetailsById(2)).thenReturn(Optional.of(itemOrderTwoSaved));
		assertSame(itemOrderOneSaved, orderService.getOrderAndItemOrderDetailsById(1).get());
		assertSame(itemOrderTwoSaved, orderService.getOrderAndItemOrderDetailsById(2).get());
		assertEquals(List.of(itemOrderDetailsOne), orderService.getOrderAndItemOrderDetailsById(1).get().getItemOrderDetailsList());
		assertEquals(List.of(itemOrderDetailsTwo, itemOrderDetailsThree), orderService.getOrderAndItemOrderDetailsById(2).get().getItemOrderDetailsList());
		verify(orderRepository, times(4)).findOrderAndItemOrderDetailsById(anyInt());
	}
	@Test
	void getAllOrders() {
		when(orderRepository.findAll()).thenReturn(List.of(itemOrderOneSaved, itemOrderTwoSaved));
		assertEquals(List.of(itemOrderOneSaved, itemOrderTwoSaved), orderService.getAll());
		verify(orderRepository, times(1)).findAll();
	}
	@Test
	void testAddOrder() {
		when(orderRepository.save(itemOrderOneToSave)).thenReturn(itemOrderOneSaved);
		when(orderRepository.save(itemOrderTwoToSave)).thenReturn(itemOrderTwoSaved);
		assertSame(itemOrderOneSaved, orderService.add(itemOrderOneToSave));
		assertSame(itemOrderTwoSaved, orderService.add(itemOrderTwoToSave));
		ArgumentCaptor<ItemOrder> captor = ArgumentCaptor.forClass(ItemOrder.class);
		verify(orderRepository, times(2)).save(captor.capture());
	}
	@Test
	void testUpdateOrder() {
		// set item order two Updated
		itemOrderTwoUpdated.setOrderId(2);
		itemOrderTwoUpdated.setUsers(userTwo);
		itemOrderTwoUpdated.setItemOrderDetailsList(List.of(itemOrderDetailsFour));
		itemOrderTwoUpdated.setTotalAmount(itemOrderDetailsFour.getItemValue());
		LocalDateTime nowTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = nowTime.format(formatter);
		itemOrderTwoUpdated.setOrderDate(formattedDate);
		when(orderRepository.save(itemOrderTwoUpdated)).thenReturn(itemOrderTwoUpdated);
		assertSame(itemOrderTwoUpdated, orderService.update(itemOrderTwoUpdated));
		ArgumentCaptor<ItemOrder> captor = ArgumentCaptor.forClass(ItemOrder.class);
		verify(orderRepository, times(1)).save(captor.capture());
	}
	@Test
	void testDeleteOrder() {
		doNothing().when(orderRepository).delete(itemOrderOneSaved);
		doNothing().when(orderRepository).delete(itemOrderTwoSaved);
		orderService.delete(itemOrderOneSaved);
		orderService.delete(itemOrderTwoSaved);
		ArgumentCaptor<ItemOrder> captor = ArgumentCaptor.forClass(ItemOrder.class);
		verify(orderRepository, times(2)).delete(captor.capture());
	}
	@Test
	void testDeleteOrderById() {
		doNothing().when(orderRepository).deleteById(1);
		doNothing().when(orderRepository).deleteById(2);
		orderService.deleteById(1);
		orderService.deleteById(2);
		verify(orderRepository, times(2)).deleteById(anyInt());
	}
}

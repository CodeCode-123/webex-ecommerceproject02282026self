package com.code.api.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.repository.IOrderDetailsRepository;
import com.code.api.service.ItemOrderDetailsServiceImpl;
import java.util.*;

@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
public class ItermOrderDetailsServiceTest {
	@Mock
	private IOrderDetailsRepository orderDetailsRepository;
	@InjectMocks
	private ItemOrderDetailsServiceImpl orderDetailsService;
	@Autowired
    private Category category;
	@Autowired
	private Item item;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOneToSave;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOneSaved;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOneUpdated;
	@Autowired
	private ItemOrderDetails itemOrderDetailsTwoToSave;
	@Autowired
	private ItemOrderDetails itemOrderDetailsTwoSaved;
	
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
		// set item order details one ToSave
		int qty = 2;
		itemOrderDetailsOneToSave.setItem(item);
		itemOrderDetailsOneToSave.setQty(qty);
		itemOrderDetailsOneToSave.setItemValue(item.getItemPrice() * qty);
		// set item order details one Saved
		itemOrderDetailsOneSaved.setItemOrderDetailsId(1);
		itemOrderDetailsOneSaved.setItem(item);
		itemOrderDetailsOneSaved.setQty(qty);
		itemOrderDetailsOneSaved.setItemValue(item.getItemPrice() * qty);
		// set item order details one Updated
		qty = 3;
		itemOrderDetailsOneUpdated.setItemOrderDetailsId(1);
		itemOrderDetailsOneUpdated.setItem(item);
		itemOrderDetailsOneUpdated.setQty(qty);
		itemOrderDetailsOneUpdated.setItemValue(item.getItemPrice() * qty);
		
		// set category two
		category.setCategoryId(2);
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Price");
		// set item two
		item.setItemId(2);
		item.setCategory(category);
		item.setItemName("Big Mac");
		item.setItemPrice(8);
		// set item order details two ToSave
		qty = 5;
		itemOrderDetailsTwoToSave.setItem(item);
		itemOrderDetailsTwoToSave.setQty(qty);
		itemOrderDetailsTwoToSave.setItemValue(item.getItemPrice() * qty);
		// set item order details two Saved
		itemOrderDetailsTwoSaved.setItemOrderDetailsId(2);
		itemOrderDetailsTwoSaved.setItem(item);
		itemOrderDetailsTwoSaved.setQty(qty);
		itemOrderDetailsTwoSaved.setItemValue(item.getItemPrice() * qty);		
	}
	@Test
	void getOrderDetailsById() {
		when(orderDetailsRepository.findById(1)).thenReturn(Optional.of(itemOrderDetailsOneSaved));
		when(orderDetailsRepository.findById(2)).thenReturn(Optional.of(itemOrderDetailsTwoSaved));
		assertSame(itemOrderDetailsOneSaved, orderDetailsService.getById(1).get());
		assertSame(itemOrderDetailsTwoSaved, orderDetailsService.getById(2).get());
		verify(orderDetailsRepository, times(2)).findById(anyInt());
	}
	@Test
	void getOrderDetailsAndItemById() {
		when(orderDetailsRepository.findItemOrderDetailsAndItemById(1)).thenReturn(Optional.of(itemOrderDetailsOneSaved));
		when(orderDetailsRepository.findItemOrderDetailsAndItemById(2)).thenReturn(Optional.of(itemOrderDetailsTwoSaved));
		assertSame(itemOrderDetailsOneSaved, orderDetailsService.getItemOrderDetailsAndItemById(1).get());
		assertSame(itemOrderDetailsTwoSaved, orderDetailsService.getItemOrderDetailsAndItemById(2).get());
		assertSame(itemOrderDetailsOneSaved.getItem(), orderDetailsService.getItemOrderDetailsAndItemById(1).get().getItem());
		assertSame(itemOrderDetailsTwoSaved.getItem(), orderDetailsService.getItemOrderDetailsAndItemById(2).get().getItem());
		verify(orderDetailsRepository, times(4)).findItemOrderDetailsAndItemById(anyInt());
	}
	@Test
	void getAllOrders() {
		when(orderDetailsRepository.findAll()).thenReturn(List.of(itemOrderDetailsOneSaved, itemOrderDetailsTwoSaved));
		assertEquals(List.of(itemOrderDetailsOneSaved, itemOrderDetailsTwoSaved), orderDetailsService.getAll());
		verify(orderDetailsRepository, times(1)).findAll();
	}
	@Test
	void testAddItemOrderDetails() {
		when(orderDetailsRepository.save(itemOrderDetailsOneToSave)).thenReturn(itemOrderDetailsOneSaved);
		when(orderDetailsRepository.save(itemOrderDetailsTwoToSave)).thenReturn(itemOrderDetailsTwoSaved);
		assertSame(itemOrderDetailsOneSaved, orderDetailsService.add(itemOrderDetailsOneToSave));
		assertSame(itemOrderDetailsTwoSaved, orderDetailsService.add(itemOrderDetailsTwoToSave));
		ArgumentCaptor<ItemOrderDetails> captor = ArgumentCaptor.forClass(ItemOrderDetails.class);
		verify(orderDetailsRepository, times(2)).save(captor.capture());
	}
	@Test
	void testUpdateItemOrderDetails() {
		when(orderDetailsRepository.save(itemOrderDetailsOneUpdated)).thenReturn(itemOrderDetailsOneUpdated);
		assertSame(itemOrderDetailsOneUpdated, orderDetailsService.update(itemOrderDetailsOneUpdated));
		ArgumentCaptor<ItemOrderDetails> captor = ArgumentCaptor.forClass(ItemOrderDetails.class);
		verify(orderDetailsRepository, times(1)).save(captor.capture());
	}
	@Test
	void testDeleteItemOrderDetails() {
		doNothing().when(orderDetailsRepository).delete(itemOrderDetailsOneSaved);
		doNothing().when(orderDetailsRepository).delete(itemOrderDetailsTwoSaved);
		orderDetailsService.delete(itemOrderDetailsOneSaved);
		orderDetailsService.delete(itemOrderDetailsTwoSaved);
		ArgumentCaptor<ItemOrderDetails> captor = ArgumentCaptor.forClass(ItemOrderDetails.class);
 		verify(orderDetailsRepository, times(2)).delete(captor.capture());
	}
	@Test
	void testDeleteItemOrderDetailsById() {
		doNothing().when(orderDetailsRepository).deleteById(1);
		doNothing().when(orderDetailsRepository).deleteById(2);
		orderDetailsService.deleteById(1);
		orderDetailsService.deleteById(2); 		
		verify(orderDetailsRepository, times(2)).deleteById(anyInt());
	}
}

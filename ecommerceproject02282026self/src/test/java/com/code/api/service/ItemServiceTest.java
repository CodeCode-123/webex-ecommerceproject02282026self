package com.code.api.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import com.code.api.repository.IItemRepository;

@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
public class ItemServiceTest {
	@Mock
	private IItemRepository itemRepository;
	@InjectMocks
	private ItemServiceImpl itemService;
	@Autowired
	private Category categoryOne;
	@Autowired
	private Category categoryTwo;
	@Autowired
	private Item itemOneToSave;
	@Autowired
	private Item itemOneSaved;
	@Autowired
	private Item itemOneUpdated;
	@Autowired
	private Item itemTwoToSave;
	@Autowired
	private Item itemTwoSaved;
	
	public ItemServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		// set category one
		categoryOne.setCategoryId(1);
		categoryOne.setCategoryName("Pizza");
		categoryOne.setCategoryDesc("Any Pizza, any toppings");
		// set itemOneToSave
		itemOneToSave.setCategory(categoryOne);
		itemOneToSave.setItemName("Cheese Pizza");
		itemOneToSave.setItemPrice(10);
		// set itemOneSaved
		itemOneSaved.setItemId(1);
		itemOneSaved.setCategory(categoryOne);
		itemOneSaved.setItemName("Cheese Pizza");
		itemOneSaved.setItemPrice(10);
		

		// set category two
		categoryTwo.setCategoryId(2);
		categoryTwo.setCategoryName("Burger");
		categoryTwo.setCategoryDesc("Best Price");
		// set itemTwoToSave
		itemTwoToSave.setCategory(categoryTwo);
		itemTwoToSave.setItemName("Big Mac");
		itemTwoToSave.setItemPrice(8);
		// set itemTwoSaved
		itemTwoSaved.setItemId(2);
		itemTwoSaved.setCategory(categoryTwo);
		itemTwoSaved.setItemName("Big Mac");
		itemTwoSaved.setItemPrice(8);		
	}
	
	@Test
	void testGetItemById() {
		when(itemRepository.findById(1)).thenReturn(Optional.of(itemOneSaved));
		when(itemRepository.findById(2)).thenReturn(Optional.of(itemTwoSaved));
		assertSame(itemOneSaved, itemService.getById(1).get());
		assertSame(itemTwoSaved, itemService.getById(2).get());
		verify(itemRepository, times(2)).findById(anyInt());
	}
	@Test
	void testGetAllItems() {
		when(itemRepository.findAll()).thenReturn(List.of(itemOneSaved, itemTwoSaved));
		assertEquals(List.of(itemOneSaved, itemTwoSaved), itemService.getAll());
		verify(itemRepository, times(1)).findAll();
	}
	@Test
	void testAddItem() {
		when(itemRepository.save(itemOneToSave)).thenReturn(itemOneSaved);
		assertEquals(itemOneSaved, itemService.add(itemOneToSave));
		ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
		verify(itemRepository, times(1)).save(captor.capture());
	}
	@Test
	void testUpdateItem() {
		// set itemOneUpdated
		itemOneUpdated.setItemId(1);
		itemOneUpdated.setCategory(categoryOne);
		itemOneUpdated.setItemName("Double Cheese Pizza");
		itemOneUpdated.setItemPrice(15);
		when(itemRepository.save(itemOneUpdated)).thenReturn(itemOneUpdated);
		assertSame(itemOneUpdated, itemService.update(itemOneUpdated));
		ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
		verify(itemRepository, times(1)).save(captor.capture());
	}
	@Test
	void testDeleteItem() {
		doNothing().when(itemRepository).delete(itemOneSaved);
		doNothing().when(itemRepository).delete(itemTwoSaved);
		itemService.delete(itemOneSaved);
		itemService.delete(itemTwoSaved);
		ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
		verify(itemRepository, times(2)).delete(captor.capture());
	}
	@Test 
	void testDeleteItemById() {
		doNothing().when(itemRepository).deleteById(1);
		doNothing().when(itemRepository).deleteById(2);
		itemService.deleteById(1);
		itemService.deleteById(2);
		verify(itemRepository, times(2)).deleteById(anyInt());
	}

}

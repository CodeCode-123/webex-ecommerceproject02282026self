package com.code.api;

import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.code.api.entity.Category;
import com.code.api.repository.ICategoryRepository;
import com.code.api.service.CategoryServiceImpl;

@SpringBootTest(classes = Ecommerceproject02282026selfApplication.class)
public class CategoryServiceTest {
	@Mock
	private ICategoryRepository categoryRepository;
	@InjectMocks
	private CategoryServiceImpl categoryService;
	@Autowired
	Category categoryOneToSave;
	@Autowired
	Category categoryOneSaved;
	@Autowired
	Category categoryOneUpdated;
	@Autowired
	Category categoryTwoToSave;
	@Autowired
	Category categoryTwoSaved;
		
	public CategoryServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		// set category one ToSave
		categoryOneToSave.setCategoryName("Pizza");
		categoryOneToSave.setCategoryDesc("Cheese Pizza");
		// set category one Saved
		categoryOneSaved.setCategoryId(1);
		categoryOneSaved.setCategoryName("Pizza");
		categoryOneSaved.setCategoryDesc("Cheese Pizza");
		// set category one Updated
		categoryOneUpdated.setCategoryId(1);
		categoryOneUpdated.setCategoryName("Pizza");
		categoryOneUpdated.setCategoryDesc("Double Cheese Pizza");

		// set category two ToSave
		categoryTwoToSave.setCategoryName("Burger");
		categoryTwoToSave.setCategoryDesc("Cheese Burger");
		// set category two Saved
		categoryTwoSaved.setCategoryId(2);
		categoryTwoSaved.setCategoryName("Burger");
		categoryTwoSaved.setCategoryDesc("Cheese Burger");
	}
	@Test
	void testGetCategoryById() {
		when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryOneSaved));
		when(categoryRepository.findById(2)).thenReturn(Optional.of(categoryTwoSaved));
		assertSame(categoryOneSaved, categoryService.getById(1).get());
		assertSame(categoryTwoSaved, categoryService.getById(2).get());
		assertEquals("Pizza", categoryService.getById(1).get().getCategoryName());
		assertEquals("Burger", categoryService.getById(2).get().getCategoryName());
		verify(categoryRepository, times(4)).findById(anyInt());
	}
	@Test
	void testGetCategoryByName() {
		when(categoryRepository.findByCategoryName("Pizza")).thenReturn(Optional.of(categoryOneSaved));
		when(categoryRepository.findByCategoryName("Burger")).thenReturn(Optional.of(categoryTwoSaved));
		assertSame(categoryOneSaved, categoryService.getCategoryByName("Pizza").get());
		assertSame(categoryTwoSaved, categoryService.getCategoryByName("Burger").get());
		assertEquals("Pizza", categoryService.getCategoryByName("Pizza").get().getCategoryName());
		assertEquals("Burger", categoryService.getCategoryByName("Burger").get().getCategoryName());
		verify(categoryRepository, times(4)).findByCategoryName(anyString());
	}
	@Test
	void testGetCategoryBySearch() {
		when(categoryRepository.findByCategoryNameLike("P")).thenReturn(List.of(categoryOneSaved));
		when(categoryRepository.findByCategoryNameLike("B")).thenReturn(List.of(categoryTwoSaved));
		assertEquals(List.of(categoryOneSaved), categoryService.search("P"));
		assertEquals(List.of(categoryTwoSaved), categoryService.search("B"));
		assertEquals(1, categoryService.search("P").size());
		assertEquals(1, categoryService.search("B").size());
		verify(categoryRepository, times(4)).findByCategoryNameLike(anyString());
	}
	@Test
	void testGetAllCategories() {
		when(categoryRepository.findAll()).thenReturn(List.of(categoryOneSaved, categoryTwoSaved));
		assertEquals(List.of(categoryOneSaved, categoryTwoSaved), categoryService.getAllCategories());
		verify(categoryRepository, times(1)).findAll();
	}
	@Test
	void testAddCategory() {
		when(categoryRepository.save(categoryOneToSave)).thenReturn(categoryOneSaved);
		assertSame(categoryOneSaved, categoryService.add(categoryOneToSave));
		ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
		verify(categoryRepository, times(1)).save(captor.capture());
	}
	@Test
	void testUpdateCategory() {
		when(categoryRepository.save(categoryOneUpdated)).thenReturn(categoryOneUpdated);
		assertSame(categoryOneUpdated, categoryService.update(categoryOneUpdated));
		ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
		verify(categoryRepository, times(1)).save(captor.capture());
	}
	@Test
	void testDeleteCategory() {
		doNothing().when(categoryRepository).delete(categoryOneSaved);;
		categoryService.delete(categoryOneSaved);
		ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
		verify(categoryRepository, times(1)).delete(captor.capture());
	}
	@Test
	void testDeleteCategoryById() {
		int id = 1;
		doNothing().when(categoryRepository).deleteById(id);
		categoryService.deleteById(id);
		verify(categoryRepository, times(1)).deleteById(anyInt());
	}

}

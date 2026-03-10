package com.code.api;

import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
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
	Category categoryOne;
	@Autowired
	Category categoryTwo;
		
	public CategoryServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		// set category one
		categoryOne.setCategoryId(1);
		categoryOne.setCategoryName("Pizza");
		categoryOne.setCategoryDesc("Cheese Pizza");
		// set category two
		categoryTwo.setCategoryId(2);
		categoryTwo.setCategoryName("Burger");
		categoryTwo.setCategoryDesc("Cheese Burger");
	}
	@Test
	void testGetCategoryById() {
		when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryOne));
		when(categoryRepository.findById(2)).thenReturn(Optional.of(categoryTwo));
		assertSame(categoryOne, categoryService.getById(1));
		assertSame(categoryTwo, categoryService.getById(2));
		assertEquals("Pizza", categoryService.getById(1).getCategoryName());
		assertEquals("Burger", categoryService.getById(2).getCategoryName());
		verify(categoryRepository, times(2)).findById(1);
		verify(categoryRepository, times(2)).findById(2);
	}
	@Test
	void testGetCategoryByName() {
		when(categoryRepository.findByCategoryName("Pizza")).thenReturn(Optional.of(categoryOne));
		when(categoryRepository.findByCategoryName("Burger")).thenReturn(Optional.of(categoryTwo));
		assertSame(categoryOne, categoryService.getCategoryByName("Pizza"));
		assertSame(categoryTwo, categoryService.getCategoryByName("Burger"));
		assertEquals("Pizza", categoryService.getCategoryByName("Pizza").getCategoryName());
		assertEquals("Burger", categoryService.getCategoryByName("Burger").getCategoryName());
		verify(categoryRepository, times(2)).findByCategoryName("Pizza");
		verify(categoryRepository, times(2)).findByCategoryName("Burger");
		
	}
	@Test
	void testGetCategoryBySearch() {
		when(categoryRepository.findByCategoryNameLike("P")).thenReturn(List.of(categoryOne));
		when(categoryRepository.findByCategoryNameLike("B")).thenReturn(List.of(categoryTwo));
		assertEquals(List.of(categoryOne), categoryService.search("P"));
		assertEquals(List.of(categoryTwo), categoryService.search("B"));
		assertEquals(1, categoryService.search("P").size());
		assertEquals(1, categoryService.search("B").size());
		verify(categoryRepository, times(2)).findByCategoryNameLike("P");
		verify(categoryRepository, times(2)).findByCategoryNameLike("B");
	}
	@Test
	void testGetAllCategories() {
		when(categoryRepository.findAll()).thenReturn(List.of(categoryOne, categoryTwo));
		assertEquals(List.of(categoryOne, categoryTwo), categoryService.getAllCategories());
		verify(categoryRepository, times(1)).findAll();
	}

}

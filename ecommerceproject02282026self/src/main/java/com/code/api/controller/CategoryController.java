package com.code.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.dto.CategoryDTO;
import com.code.api.dto.UsersDTO;
import com.code.api.entity.Category;
import com.code.api.entity.Users;
import com.code.api.exception.ResourceNotFoundException;
import com.code.api.service.*;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {
    //add the dependency
	@Autowired
	ICategoryService categoryService;
	
	// root mapping
	@GetMapping("/")
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}
	@GetMapping(value="/{id}")
	public Category getCategoryById(@PathVariable int id) {
		Category dbCategory = categoryService.getById(id);
		if (dbCategory == null) {
			throw new ResourceNotFoundException("Category", "categoryId", String.valueOf(id));
		}
		return categoryService.getById(id);
		
	}
	@GetMapping(value="/search/{catname}")
	public List<Category> search(@PathVariable("catname") String catname) {
		List<Category> dbCategory = categoryService.search(catname);
		if (dbCategory == null) {
			throw new ResourceNotFoundException("Category", "categoryName", catname);
		}
		return categoryService.search(catname);
	}
	@PostMapping(value="/create")
	public Category createCategory(@RequestBody Category category) {
		return categoryService.add(category);
	}
	@PutMapping(value="/edit")
	public Category editCategory(@RequestBody Category category) {
		return categoryService.update(category);
	}
	@PatchMapping(value="/edit/{id}")
	public Category editCategoryById(@PathVariable("id") int id, @RequestBody CategoryDTO categoryDTO) {
		Category dbCategory = categoryService.getById(id);
		if (dbCategory == null) {
			throw new ResourceNotFoundException("Category", "categoryId", String.valueOf(id));
		}
		if (categoryDTO.getCategoryName() != null && categoryDTO.getCategoryName().trim().length() > 0) {
			dbCategory.setCategoryName(categoryDTO.getCategoryName());
		}
		if (categoryDTO.getCategoryDesc() != null && categoryDTO.getCategoryDesc().trim().length() > 0) {
			dbCategory.setCategoryDesc(categoryDTO.getCategoryDesc());
		}
		return categoryService.update(dbCategory);
	}
	@DeleteMapping(value="/delete/{id}")
	public String deleteCategory(@PathVariable("id") int id) {
		Category dbCategory = categoryService.getById(id);
		if (dbCategory == null) {
			throw new ResourceNotFoundException("Category", "categoryId", String.valueOf(id));
		}
		return categoryService.delete(id);
	}
}

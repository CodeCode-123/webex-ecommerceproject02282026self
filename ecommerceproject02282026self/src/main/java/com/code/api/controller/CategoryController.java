package com.code.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.entity.Category;
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
		return categoryService.getById(id);
		
	}
	@GetMapping(value="/search/{catname}")
	public List<Category> search(@PathVariable("catname") String catname) {
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
	@DeleteMapping(value="/delete/{id}")
	public String deleteCategory(@PathVariable("id") int id) {
		return categoryService.delete(id);
		
	}
}

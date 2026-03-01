package com.code.api.service;

import java.util.List;


import com.code.api.entity.Category;

public interface ICategoryService {
	// declare the method that we want for the student
	public Category add(Category category);

	public Category update(Category category);

	public String delete(Category category);

	public String delete(int id);

	// create some method to get the student
	public List<Category> getAllCategories();

	public Category getById(int id);

	public Category getCategoryByName(String catname);

	public List<Category> search(String catname);
}

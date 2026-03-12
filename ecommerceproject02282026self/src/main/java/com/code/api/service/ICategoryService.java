package com.code.api.service;

import java.util.List;
import java.util.Optional;

import com.code.api.entity.Category;

public interface ICategoryService {
	Category add(Category category);
	Category update(Category category);
	void delete(Category category);
	void deleteById(int id);
	List<Category> getAllCategories();
	Optional<Category> getById(int id);
	Optional<Category> getCategoryByName(String catname);
	List<Category> search(String catname);
}

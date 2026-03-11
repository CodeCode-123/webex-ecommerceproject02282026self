package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.repository.*;
import com.code.api.entity.Category;
//Add transaction and Service
@Service
@Transactional //will create the bean for the session.beginTranction ----- session.commit
public class CategoryServiceImpl implements ICategoryService {
	//Inject CategoryDAO
	@Autowired
	ICategoryRepository categoryRepository;
	@Override
	public Category add(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category update(Category category) {
	    return categoryRepository.save(category);
	}

	
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> getById(int id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Optional<Category> getCategoryByName(String catname){
		return categoryRepository.findByCategoryName(catname);
	}

	@Override
	public List<Category> search(String catname) {
		return categoryRepository.findByCategoryNameLike(catname);
	}

	@Override
	public void delete(Category category) {
		categoryRepository.delete(category);
	}

	@Override
	public void deleteById(int id) {
		categoryRepository.deleteById(id);
	}

}

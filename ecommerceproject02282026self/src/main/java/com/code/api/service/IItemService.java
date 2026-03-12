package com.code.api.service;

import java.util.List;
import java.util.Optional;

import com.code.api.entity.Item;

public interface IItemService {
	Item add(Item item);
	Item update(Item item);
	void delete(Item item);
	void deleteById(int id);
	List<Item> getAll();
	Optional<Item> getById(int id);
}

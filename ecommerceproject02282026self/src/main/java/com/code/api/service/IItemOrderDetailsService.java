package com.code.api.service;

import java.util.List;
import java.util.Optional;

import com.code.api.entity.ItemOrderDetails;

public interface IItemOrderDetailsService {
	ItemOrderDetails add(ItemOrderDetails itemOrder);
	ItemOrderDetails update(ItemOrderDetails itemOrder);
	void delete(ItemOrderDetails itemOrder);
	void deleteById(int id);
	List<ItemOrderDetails> getAll();
	Optional<ItemOrderDetails> getById(int id);
	Optional<ItemOrderDetails> getItemOrderDetailsAndItemById(int id);
}

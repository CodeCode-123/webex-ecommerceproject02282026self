package com.code.api.service;

import java.util.List;
import java.util.Optional;

import com.code.api.entity.ItemOrderDetails;

public interface IItemOrderDetailsService {
	public ItemOrderDetails add(ItemOrderDetails itemOrder);
	public ItemOrderDetails update(ItemOrderDetails itemOrder);
	public String delete(ItemOrderDetails itemOrder);
	public String delete(int id);
	public List<ItemOrderDetails> getAll();
	public Optional<ItemOrderDetails> getById(int id);
	public Optional<ItemOrderDetails> getOrderDetailsAndItemById(int id);
}

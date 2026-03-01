package com.code.api.service;

import java.util.List;


import com.code.api.entity.ItemOrderDetails;

public interface IItemOrderDetailsService {
	public ItemOrderDetails add(ItemOrderDetails itemOrder);
	public ItemOrderDetails update(ItemOrderDetails itemOrder);
	public String delete(ItemOrderDetails itemOrder);
	public String delete(int id);
	//create some method to get the student
	public List<ItemOrderDetails> getAll();
	public List<ItemOrderDetails> getByOrderId(int id);
	public ItemOrderDetails getById(int id);
}

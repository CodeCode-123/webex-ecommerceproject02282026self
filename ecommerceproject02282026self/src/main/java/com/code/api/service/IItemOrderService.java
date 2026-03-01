package com.code.api.service;

import java.util.List;
import com.code.api.entity.ItemOrder;

public interface IItemOrderService {
	public ItemOrder add(ItemOrder itemOrder);
	public ItemOrder update(ItemOrder itemOrder);
	public String delete(ItemOrder itemOrder);
	public String delete(int id);
	//create some method to get the student
	public List<ItemOrder> getAll();
	public ItemOrder getById(int id);
}

package com.code.api.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;

public interface IItemOrderService {
	public ItemOrder add(ItemOrder itemOrder);
	public ItemOrder update(ItemOrder itemOrder);
	public String delete(ItemOrder itemOrder);
	public String delete(int id);
	//create some method to get the student
	public List<ItemOrder> getAll();
	public ItemOrder getById(int id);
	public List<ItemOrderDetails> findOrderAndItemOrderDetailsById(@Param("data") int orderId);
}

package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;

public interface IItemOrderService {
	public ItemOrder add(ItemOrder itemOrder);
	public ItemOrder update(ItemOrder itemOrder);
	public String delete(ItemOrder itemOrder);
	public String delete(int id);
	public List<ItemOrder> getAll();
	public Optional<ItemOrder> getById(int id);
	public Optional<ItemOrder> getOrderAndItemOrderDetailsById(@Param("data") int orderId);
}

package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;

public interface IItemOrderService {
	ItemOrder add(ItemOrder itemOrder);
	ItemOrder update(ItemOrder itemOrder);
	void delete(ItemOrder itemOrder);
	void deleteById(int id);
	List<ItemOrder> getAll();
	Optional<ItemOrder> getById(int id);
	Optional<ItemOrder> getOrderAndItemOrderDetailsById(@Param("data") int orderId);
}

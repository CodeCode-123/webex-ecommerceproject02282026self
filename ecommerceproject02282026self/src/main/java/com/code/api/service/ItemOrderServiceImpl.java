package com.code.api.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.code.api.repository.*;
import com.code.api.entity.ItemOrder;

@Service
@Transactional
public class ItemOrderServiceImpl implements IItemOrderService {
    @Autowired
    IOrdersRepository ordersRepository;
	@Override
	public ItemOrder add(ItemOrder itemOrder) {
		return ordersRepository.save(itemOrder);
	}

	@Override
	public ItemOrder update(ItemOrder itemOrder) {
		return ordersRepository.save(itemOrder);
	}

	@Override
	public void delete(ItemOrder itemOrder) {
		ordersRepository.delete(itemOrder);
	}

	@Override
	public void deleteById(int id) {
		ordersRepository.deleteById(id);
	}

	@Override
	public List<ItemOrder> getAll() {
		return ordersRepository.findAll();
	}

	@Override
	public Optional<ItemOrder> getById(int id) {
		return ordersRepository.findById(id);
	}

	@Override
	public Optional<ItemOrder> getOrderAndItemOrderDetailsById(int orderId) {
		return ordersRepository.findOrderAndItemOrderDetailsById(orderId);
	}

	@Override
	public Optional<ItemOrder> getOrderByRazorpayOrderId(String razorpayOrderId) {
		return ordersRepository.findByRazorpayOrderId(razorpayOrderId);
	}
}

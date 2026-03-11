package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.repository.*;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
@Service
@Transactional
public class ItemOrderServiceImpl implements IItemOrderService {
    @Autowired
    IOrdersRepository ordersRepository;
	@Override
	public ItemOrder add(ItemOrder itemOrder) {
		// TODO Auto-generated method stub
		return ordersRepository.save(itemOrder);
	}

	@Override
	public ItemOrder update(ItemOrder itemOrder) {
		// TODO Auto-generated method stub
		return ordersRepository.save(itemOrder);
	}

	@Override
	public String delete(ItemOrder itemOrder) {
		// TODO Auto-generated method stub
		ordersRepository.delete(itemOrder);
		return "Record is deleted successfully";
	}

	@Override
	public String delete(int id) {
		// TODO Auto-generated method stub
		// find by id
		Optional<ItemOrder> itemOrderOptional = ordersRepository.findById(id);
		if (itemOrderOptional.isPresent()) {
			ordersRepository.delete(itemOrderOptional.get());
			return "Record is deleted successfully";
		}
		return "Item order with Id " + id + " not found";
	}

	@Override
	public List<ItemOrder> getAll() {
		// TODO Auto-generated method stub
		return ordersRepository.findAll();
	}

	@Override
	public Optional<ItemOrder> getById(int id) {
		// TODO Auto-generated method stub
		return ordersRepository.findById(id);
//		Optional<ItemOrder> itemOrderOptional = ordersRepository.findById(id);
//		ItemOrder itemOrder = null;
//		if (itemOrderOptional.isPresent()) {
//			itemOrder = itemOrderOptional.get();
//		}
//		return itemOrder;
	}

	@Override
	public Optional<ItemOrder> getOrderAndItemOrderDetailsById(int orderId) {
		// TODO Auto-generated method stub
		return ordersRepository.findOrderAndItemOrderDetailsById(orderId);
	}

}

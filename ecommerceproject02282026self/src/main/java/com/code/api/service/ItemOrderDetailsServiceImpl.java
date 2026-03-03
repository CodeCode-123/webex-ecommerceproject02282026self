package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.repository.*; 
import com.code.api.entity.ItemOrderDetails;
@Service
@Transactional
public class ItemOrderDetailsServiceImpl implements IItemOrderDetailsService {
    @Autowired
    IOrderDetailsRepository orderDetailsRepository;
	@Override
	public ItemOrderDetails add(ItemOrderDetails itemOrderDetails) {
		// TODO Auto-generated method stub
		return orderDetailsRepository.save(itemOrderDetails);
	}

	@Override
	public ItemOrderDetails update(ItemOrderDetails itemOrderDetails) {
		// TODO Auto-generated method stub
		return orderDetailsRepository.save(itemOrderDetails);
	}

	@Override
	public String delete(ItemOrderDetails itemOrderDetails) {
		// TODO Auto-generated method stub
		orderDetailsRepository.delete(itemOrderDetails);
		return "Record is deleted successfully";
	}

	@Override
	public String delete(int id) {
		// TODO Auto-generated method stub
		// find by Id
		Optional<ItemOrderDetails> itemOrderDetailsOptional = orderDetailsRepository.findById(id);
		// check if the item order details is present or not 
		if (itemOrderDetailsOptional.isPresent()) {
			orderDetailsRepository.delete(itemOrderDetailsOptional.get());
			return "Record is deleted successfully";
		}
		return "Item order details with Id " + id + " not found";
	}

	@Override
	public List<ItemOrderDetails> getAll() {
		// TODO Auto-generated method stub
		return orderDetailsRepository.findAll();
	}

	@Override
	public ItemOrderDetails getById(int id) {
		// TODO Auto-generated method stub
		// find by Id
		Optional<ItemOrderDetails> itemOrderDetailsOptional = orderDetailsRepository.findById(id);
		ItemOrderDetails itemOrderDetails = null;
		// check if the category is present or not
		if (itemOrderDetailsOptional.isPresent()) {
			itemOrderDetails = itemOrderDetailsOptional.get();
		}
		return itemOrderDetails;
	}

	/*
	@Override
	public List<ItemOrderDetails> getByOrderId(int id) {
		// TODO Auto-generated method stub
		return orderDetailsRepository.findByOrderId(id);
	}*/

}

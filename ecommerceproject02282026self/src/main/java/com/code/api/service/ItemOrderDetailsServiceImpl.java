package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.repository.*; 
import com.code.api.entity.ItemOrderDetails;
@Service
@Transactional
@Primary
public class ItemOrderDetailsServiceImpl implements IItemOrderDetailsService {
    @Autowired
    IOrderDetailsRepository orderDetailsRepository;
	@Override
	public ItemOrderDetails add(ItemOrderDetails itemOrderDetails) {
		return orderDetailsRepository.save(itemOrderDetails);
	}

	@Override
	public ItemOrderDetails update(ItemOrderDetails itemOrderDetails) {
		return orderDetailsRepository.save(itemOrderDetails);
	}

	@Override
	public void delete(ItemOrderDetails itemOrderDetails) {
		orderDetailsRepository.delete(itemOrderDetails);
	}

	@Override
	public void deleteById(int id) {
		orderDetailsRepository.deleteById(id);
	}

	@Override
	public List<ItemOrderDetails> getAll() {
		return orderDetailsRepository.findAll();
	}

	@Override
	public Optional<ItemOrderDetails> getById(int id) {
		return orderDetailsRepository.findById(id);
	}

	@Override
	public Optional<ItemOrderDetails> getItemOrderDetailsAndItemById(int id) {
		return orderDetailsRepository.findItemOrderDetailsAndItemById(id);
	}
}

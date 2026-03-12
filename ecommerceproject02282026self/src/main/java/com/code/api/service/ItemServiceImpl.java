package com.code.api.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.code.api.repository.*;
import com.code.api.entity.Item;

@Service
@Transactional
public class ItemServiceImpl implements IItemService {
	@Autowired
	IItemRepository itemRepository;
	
	@Override
	public Item add(Item item) {
		return itemRepository.save(item);
	}
	@Override
	public Item update(Item item) {
		return itemRepository.save(item);
	}
	@Override
	public void delete(Item item) {
		itemRepository.delete(item);
	}
	@Override
	public void deleteById(int id) {
		itemRepository.deleteById(id);
	}
	@Override
	public List<Item> getAll() {
		return itemRepository.findAll();
	}
	@Override
	public Optional<Item> getById(int id) {
		return itemRepository.findById(id);
	}
}

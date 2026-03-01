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
		// TODO Auto-generated method stub
		return itemRepository.save(item);
	}

	@Override
	public Item update(Item item) {
		// TODO Auto-generated method stub
		return itemRepository.save(item);
	}

	@Override
	public String delete(Item item) {
		// TODO Auto-generated method stub
		itemRepository.delete(item);
		return "Record is deleted successfully";
	}

	@Override
	public String delete(int id) {
		// TODO Auto-generated method stub
		// find by id
		Optional<Item> itemOptional = itemRepository.findById(id);
		// check if null
		if (itemOptional.isPresent()) {
			itemRepository.delete(itemOptional.get());
			return "Record is deleted successfully";
		}
		return "Item with id " + id + " is not found";
	}

	@Override
	public List<Item> getAll() {
		// TODO Auto-generated method stub
		return itemRepository.findAll();
	}

	@Override
	public Item getById(int id) {
		// TODO Auto-generated method stub
		// find by id
		Optional<Item> itemOptional = itemRepository.findById(id);
		Item item = null;
		if (itemOptional.isPresent()) {
			item = itemOptional.get();
		}
		return item;
	}
}

package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.code.api.service.IItemService;

import jakarta.validation.Valid;

import com.code.api.dto.ItemDTO;
import com.code.api.entity.*;
import com.code.api.exception.ResourceNotFoundException;

import java.util.*;

@RestController
@RequestMapping("/api/item/")
public class ItemController {
	@Autowired
	IItemService itemService;
	
	@GetMapping("/")
	public List<Item> getAllItems() {
		return itemService.getAll();
	}
	@GetMapping("/{id}")
	public Item getItemById(@PathVariable int id) {
		Optional<Item> dbItem = itemService.getById(id);
		if (dbItem.isEmpty()) {
			throw new ResourceNotFoundException("Item", "itemId", String.valueOf(id));
		}
		return itemService.getById(id).get();
	}
	@PostMapping("/create")
	public Item createItem(@RequestBody Item item) {
		return itemService.add(item);
	}
	@PutMapping("/edit")
	public Item editItem(@RequestBody Item item) {
		return itemService.update(item);
	}
	@PatchMapping("/edit/{id}")
	public Item editItemById(@PathVariable("id") int id, @Valid @RequestBody ItemDTO itemDTO) {
		Optional<Item> dbItem = itemService.getById(id);
		if (dbItem.isEmpty()) {
			throw new ResourceNotFoundException("Item", "itemId", String.valueOf(id));
		}
		if (itemDTO.getItemName() != null && itemDTO.getItemName().trim().length() > 0) {
			dbItem.get().setItemName(itemDTO.getItemName());
		}
		if (itemDTO.getItemPrice() > 0) {
			dbItem.get().setItemPrice(itemDTO.getItemPrice());
		}
		if (itemDTO.getImageData() != null && itemDTO.getImageData().length > 0) {
			dbItem.get().setImageData(itemDTO.getImageData());
		}
		if (itemDTO.getCategory() != null && itemDTO.getCategory().getCategoryId() > 0) {
			dbItem.get().setCategory(itemDTO.getCategory());
		}
		return itemService.update(dbItem.get());
	}
	@DeleteMapping("/delete/{id}")
	public String deleteItem(@PathVariable("id") int id) {
		Optional<Item> dbItem = itemService.getById(id);
		if (dbItem.isEmpty()) {
			throw new ResourceNotFoundException("Item", "itemId", String.valueOf(id));
		}
		itemService.deleteById(id);
		return "Record is deleted successfully";
	}	
}

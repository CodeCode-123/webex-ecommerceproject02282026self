package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.code.api.service.IItemService;
import com.code.api.entity.*;
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
		return itemService.getById(id);
	}
	@PostMapping("/create")
	public Item createItem(@RequestBody Item item) {
		return itemService.add(item);
	}
	@PutMapping("/edit")
	public Item editItem(@RequestBody Item item) {
		return itemService.update(item);
	}
	@DeleteMapping("/delete/{id}")
	public String deleteItem(@PathVariable("id") int id) {
		return itemService.delete(id);
	}	
}

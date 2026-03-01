package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.code.api.service.*;
import com.code.api.entity.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private IItemOrderService itemOrderService;
	
	@GetMapping("/")
	public List<ItemOrder> getAllOrders() {
		return itemOrderService.getAll();
	}
	@GetMapping("/{id}")
	public ItemOrder getOrderById(@PathVariable("id") int id) {
		return itemOrderService.getById(id);
	}
	@PostMapping("/create")
	public ItemOrder createOrder(@RequestBody ItemOrder itemOrder) {
		return itemOrderService.add(itemOrder);
	}
	@PutMapping("/edit")
	public ItemOrder editOrder(@RequestBody ItemOrder itemOrder) {
		return itemOrderService.update(itemOrder);
	}
	@DeleteMapping("/delete/{id}")
	public String deleteOrder(@PathVariable("id") int id) {
		return itemOrderService.delete(id);
	}

}

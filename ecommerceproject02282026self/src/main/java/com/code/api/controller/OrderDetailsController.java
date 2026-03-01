package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.service.IItemOrderDetailsService;
import com.code.api.entity.*;
import java.util.*;

@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailsController {
	@Autowired
	private IItemOrderDetailsService orderDetailsService;
	
	@GetMapping("/")
	public List<ItemOrderDetails> getAllOrderDetails() {
		return orderDetailsService.getAll();
	}
	@GetMapping("/{id}")
	public ItemOrderDetails getOrderDetailsById(@PathVariable("id") int id) {
		return orderDetailsService.getById(id);
	}
	@PostMapping("/create")
	public ItemOrderDetails createOrderDetails(@RequestBody ItemOrderDetails itemOrderDetails) {
		return orderDetailsService.add(itemOrderDetails);
	}
	@PutMapping("/edit")
	public ItemOrderDetails editOrderDetails(@RequestBody ItemOrderDetails itemOrderDetails) {
		return orderDetailsService.update(itemOrderDetails);
	}
	@DeleteMapping("/delete/{id}")
	public String deleteOrderDetails(@PathVariable("id") int id) {
		return orderDetailsService.delete(id);
	}

}

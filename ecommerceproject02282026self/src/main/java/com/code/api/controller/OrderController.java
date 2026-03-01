package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.code.api.service.*;
import com.code.api.dto.CartItems;
import com.code.api.dto.OrderRequestDTO;
import com.code.api.entity.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private IItemOrderService itemOrderService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IItemOrderDetailsService itemOrderDetailsService;
	
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
	@PostMapping("placeholder") // create an order with items
	public ResponseEntity<ItemOrder> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
		System.out.println("User id: " + orderRequestDTO.getUserId());
		int userId = orderRequestDTO.getUserId();
		Users users = userService.getUserById(userId);
		ItemOrder order = new ItemOrder();
		if (users == null) {
			return ResponseEntity.ok(order);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = order.getOrderDate().formatted(formatter);
		order.setOrderDate(formattedDate);
		order.setTotalAmount(orderRequestDTO.getTotalAmount());
		System.out.println("Order date: " + order.getOrderDate());
		order.setUsers(users);
		itemOrderService.add(order);
		ItemOrderDetails detail = null;
		for (CartItems item:orderRequestDTO.getItems()) {
			detail = new ItemOrderDetails();
			detail.setProductName(item.getItemName());
			detail.setCategoryName(item.getCategory().getCategoryName());
			detail.setPrice(item.getItemPrice());
			detail.setQty(item.getQty());
			detail.setItemValue(item.getItemPrice());
			detail.setItemOrder(order);
			itemOrderDetailsService.add(detail);
		}
		return ResponseEntity.ok(order);
	}
	

}

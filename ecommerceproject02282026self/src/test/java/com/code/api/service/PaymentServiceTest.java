package com.code.api.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.code.api.Ecommerceproject02282026selfApplication;
import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Payment;
import com.code.api.entity.Users;
import com.code.api.repository.IPaymentRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Ecommerceproject02282026selfApplication.class)
public class PaymentServiceTest {
	@Mock
	private IPaymentRepository paymentRepository;
	@InjectMocks
	private PaymentServiceImpl paymentService;
	@Autowired
	private Payment paymentOneToSave;
	@Autowired
	private Payment paymentOneSaved;
	@Autowired
	private Payment paymentTwoToSave;
	@Autowired
	private Payment paymentTwoSaved;
	@Autowired
	private Users userOne;
	@Autowired
	private Users userTwo;
	@Autowired
    private Category category;
	@Autowired
	private Item item;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOne;
	@Autowired
	private ItemOrderDetails itemOrderDetailsTwo;
	@Autowired
	private ItemOrderDetails itemOrderDetailsThree;
	@Autowired
	private ItemOrderDetails itemOrderDetailsFour;
	@Autowired
	private ItemOrder itemOrderOneToSave;
	@Autowired
	private ItemOrder itemOrderOneSaved;
	@Autowired
	private ItemOrder itemOrderTwoToSave;
	@Autowired
	private ItemOrder itemOrderTwoSaved;
	
	
	
	public PaymentServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	@BeforeEach
	public void beforeEach() {
		// set user one
		userOne.setId(1);
		userOne.setEmailId("admin@abc.com");
		userOne.setFirstName("admin");
		userOne.setLastName("admin");
		userOne.setPassword("1234");
		// set user two
		userTwo.setId(2);
		userTwo.setEmailId("customer@abc.com");
		userTwo.setFirstName("customer");
		userTwo.setLastName("customer");
		userTwo.setPassword("1234");

		// set category one
		category.setCategoryId(1);
		category.setCategoryName("Pizza");
		category.setCategoryDesc("Any Pizza, any toppings");
		// set item one
		item.setItemId(1);
		item.setCategory(category);
		item.setItemName("Cheese Pizza");
		item.setItemPrice(10);
		// set item order details one, 20
		int qty = 2;
		itemOrderDetailsOne.setItemOrderDetailsId(1);
		itemOrderDetailsOne.setItem(item);
		itemOrderDetailsOne.setQty(2);
		itemOrderDetailsOne.setItemValue(item.getItemPrice() * qty);

		// set category two
		category.setCategoryId(2);
		category.setCategoryName("Burger");
		category.setCategoryDesc("Best Price");
		// set item two
		item.setItemId(2);
		item.setCategory(category);
		item.setItemName("Big Mac");
		item.setItemPrice(8);
		// set item order details two, 24
		qty = 3;
		itemOrderDetailsTwo.setItemOrderDetailsId(2);
		itemOrderDetailsTwo.setItem(item);
		itemOrderDetailsTwo.setQty(qty);
		itemOrderDetailsTwo.setItemValue(item.getItemPrice() * qty);

		// set item three
		item.setItemId(3);
		item.setCategory(category);
		item.setItemName("Cheese Burger");
		item.setItemPrice(6);
		// set item order details three, 30
		qty = 5;
		itemOrderDetailsThree.setItemOrderDetailsId(3);
		itemOrderDetailsThree.setItem(item);
		itemOrderDetailsThree.setQty(qty);
		itemOrderDetailsThree.setItemValue(item.getItemPrice() * qty);

		// set item four
		item.setItemId(4);
		item.setCategory(category);
		item.setItemName("Double Cheese Burger");
		item.setItemPrice(8);

		// set item order details four
		qty = 6;
		itemOrderDetailsFour.setItemOrderDetailsId(4);
		itemOrderDetailsFour.setItem(item);
		itemOrderDetailsFour.setQty(qty);
		itemOrderDetailsFour.setItemValue(item.getItemPrice() * qty);

		// set item order one ToSave
		itemOrderOneToSave.setUsers(userOne);
		itemOrderOneToSave.setItemOrderDetailsList(List.of(itemOrderDetailsOne));
		itemOrderOneToSave.setTotalAmount(itemOrderDetailsOne.getItemValue());
		LocalDateTime nowTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = nowTime.format(formatter);
		itemOrderOneToSave.setOrderDate(formattedDate);
		// set item order one Saved
		itemOrderOneSaved.setOrderId(1);
		itemOrderOneSaved.setUsers(userOne);
		itemOrderOneSaved.setItemOrderDetailsList(List.of(itemOrderDetailsOne));
		itemOrderOneSaved.setTotalAmount(itemOrderDetailsOne.getItemValue());
		itemOrderOneToSave.setOrderDate(formattedDate);

		// set item order two ToSave
		itemOrderTwoToSave.setUsers(userTwo);
		itemOrderTwoToSave.setItemOrderDetailsList(List.of(itemOrderDetailsTwo, itemOrderDetailsThree));
		itemOrderTwoToSave.setTotalAmount(itemOrderDetailsTwo.getItemValue() + itemOrderDetailsThree.getItemValue());
		nowTime = LocalDateTime.now();
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		formattedDate = nowTime.format(formatter);
		itemOrderTwoToSave.setOrderDate(formattedDate);
		// set item order two Saved
		itemOrderTwoSaved.setOrderId(2);
		itemOrderTwoSaved.setUsers(userTwo);
		itemOrderTwoSaved.setItemOrderDetailsList(List.of(itemOrderDetailsTwo, itemOrderDetailsThree));
		itemOrderTwoSaved.setTotalAmount(itemOrderDetailsTwo.getItemValue() + itemOrderDetailsThree.getItemValue());
		itemOrderTwoSaved.setOrderDate(formattedDate);
		
		// set paymentOneToSave
		paymentOneToSave.setRazorpayOrderId("order_SSU0gfeRAOFIgn");
		paymentOneToSave.setRazorpayPaymentId("txn_1773792031843");
		paymentOneToSave.setAmount(2000);
		paymentOneToSave.setOrder(itemOrderOneSaved);
		paymentOneToSave.setPaidAt(LocalDateTime.now());
		paymentOneToSave.setStatus("created");	
		paymentOneToSave.setOrder(itemOrderOneSaved);
		// set paymentOneSaved
		paymentOneSaved.setId(1);
		paymentOneSaved.setRazorpayOrderId("order_SSU0gfeRAOFIgn");
		paymentOneSaved.setRazorpayPaymentId("txn_1773792031843");
		paymentOneSaved.setAmount(2000);
		paymentOneSaved.setOrder(itemOrderOneSaved);
		paymentOneSaved.setPaidAt(LocalDateTime.now());
		paymentOneSaved.setStatus("created");
		
		// set paymentTwoToSave
		paymentTwoToSave.setRazorpayOrderId("order_SRJjf0IIfBpI8i");
		paymentTwoToSave.setRazorpayPaymentId("txn_1773537508412");
		paymentTwoToSave.setAmount(5400);
		paymentTwoToSave.setOrder(itemOrderTwoSaved);
		paymentTwoToSave.setPaidAt(LocalDateTime.now());
		paymentTwoToSave.setStatus("created");
		// set paymentTwoSaved
		paymentTwoSaved.setId(2);
		paymentTwoSaved.setRazorpayOrderId("order_SRJjf0IIfBpI8i");
		paymentTwoSaved.setRazorpayPaymentId("txn_1773537508412");
		paymentTwoSaved.setAmount(5400);
		paymentTwoSaved.setOrder(itemOrderTwoSaved);
		paymentTwoSaved.setPaidAt(LocalDateTime.now());
		paymentTwoSaved.setStatus("created");		
	}
	@Test
	void testGetPaymentById() {
		when(paymentRepository.findById(1)).thenReturn(Optional.of(paymentOneSaved));
		when(paymentRepository.findById(2)).thenReturn(Optional.of(paymentTwoSaved));
		assertSame(paymentOneSaved, paymentService.getPaymentById(1).get());
		assertSame(paymentTwoSaved, paymentService.getPaymentById(2).get());
		assertEquals(20, paymentService.getPaymentById(1).get().getOrder().getTotalAmount());
		assertEquals(54, paymentService.getPaymentById(2).get().getOrder().getTotalAmount());
		verify(paymentRepository, times(4)).findById(anyInt());
	}
	@Test
	void testGetAllPayments() {
		when(paymentRepository.findAll()).thenReturn(List.of(paymentOneSaved, paymentTwoSaved));
		assertEquals(List.of(paymentOneSaved, paymentTwoSaved), paymentService.getAllPayments());
		verify(paymentRepository, times(1)).findAll();
	}
	@Test
	void testCreatePayment() {
		when(paymentRepository.save(paymentOneToSave)).thenReturn(paymentOneSaved);
		when(paymentRepository.save(paymentTwoToSave)).thenReturn(paymentTwoSaved);
		assertSame(paymentOneSaved, paymentService.createPayment(paymentOneToSave));
		assertSame(paymentTwoSaved, paymentService.createPayment(paymentTwoToSave));
		ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class); 
		verify(paymentRepository, times(2)).save(captor.capture());
	}

}

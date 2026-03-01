package com.code.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.code.api.entity.ItemOrderDetails;


@Repository
public interface IOrderDetailsRepository extends JpaRepository<ItemOrderDetails, Integer>{
	List<ItemOrderDetails> findByItemOrderId(int orderId);
}

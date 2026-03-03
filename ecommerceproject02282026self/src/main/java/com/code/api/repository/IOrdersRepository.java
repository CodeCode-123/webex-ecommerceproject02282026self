package com.code.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;

@Repository
public interface IOrdersRepository extends JpaRepository<ItemOrder, Integer>{
	@Query(value="SELECT i FROM ItemOrder i JOIN FETCH i.itemOrderDetailsList WHERE i.orderId=:data")
	List<ItemOrderDetails> findOrderAndItemOrderDetailsById(@Param("data") int orderId);
}

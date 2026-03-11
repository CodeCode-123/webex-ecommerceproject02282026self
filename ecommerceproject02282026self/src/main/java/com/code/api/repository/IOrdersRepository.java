package com.code.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.code.api.entity.ItemOrder;

@Repository
public interface IOrdersRepository extends JpaRepository<ItemOrder, Integer>{
	@Query(value="SELECT i FROM ItemOrder i JOIN FETCH i.itemOrderDetailsList WHERE i.orderId=:data")
	Optional<ItemOrder> findOrderAndItemOrderDetailsById(@Param("data") int orderId);
}

package com.code.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.code.api.entity.ItemOrder;

@Repository
public interface IOrdersRepository extends JpaRepository<ItemOrder, Integer>{

}

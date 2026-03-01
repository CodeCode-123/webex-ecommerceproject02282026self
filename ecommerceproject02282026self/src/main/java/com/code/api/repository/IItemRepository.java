package com.code.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.code.api.entity.Item;

@Repository
public interface IItemRepository extends JpaRepository <Item, Integer> {

}

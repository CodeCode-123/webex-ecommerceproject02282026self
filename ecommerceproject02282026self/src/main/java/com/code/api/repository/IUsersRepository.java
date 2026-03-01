package com.code.api.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.code.api.entity.*;

@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer> {
	//method
	Optional<Users> findByEmailId(String emailId);
}

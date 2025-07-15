package com.smart.smartContactManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.smartContactManager.entities.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmail(String username);
	
	
}

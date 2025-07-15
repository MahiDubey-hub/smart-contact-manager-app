package com.smart.smartContactManager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.smartContactManager.entities.Contact;
import com.smart.smartContactManager.entities.User;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	//current page, contacts per page(5)
	Page<Contact> findByUser(User user, Pageable pageable);
	
	void deleteByUser(User user);
	
	List<Contact> findByNameContainingAndUser(String name, User user);
}

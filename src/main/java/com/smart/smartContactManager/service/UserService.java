package com.smart.smartContactManager.service;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.smart.smartContactManager.entities.Contact;
import com.smart.smartContactManager.entities.User;

import jakarta.servlet.http.HttpSession;

public interface UserService {

	void addOrUpdateContactService(Contact contact, MultipartFile file, Principal principal, HttpSession session);
	Page<Contact> getContactsWithPaginationAndSorting(int page, String sortField, String sortDir, Principal principal);
	Optional<Contact> showContactDetail(Integer cId, Principal principal);
	boolean deleteContactService(Integer cId, Principal principal);
	Optional<Contact> updateContactService(Integer cId, HttpSession session);
	boolean changePassword(String oldPassword, String newPassword, Principal principal, HttpSession session);
	User getUserByemail(Principal principal);
	void processUpdateUserService(User user, MultipartFile file);
	void deleteUserService(Principal principal);
	
}

package com.smart.smartContactManager.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smart.smartContactManager.entities.Contact;
import com.smart.smartContactManager.entities.User;
import com.smart.smartContactManager.helper.Message;
import com.smart.smartContactManager.repository.ContactRepository;
import com.smart.smartContactManager.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public void addOrUpdateContactService(Contact contact, MultipartFile file, Principal principal, HttpSession session) {
		
		try {
			if (!file.isEmpty()) {
		        String fileName = file.getOriginalFilename();
		        // Save the file where needed (filesystem or database)
		        contact.setImage(fileName); // Save name in DB
		        
		        File saveFile = new ClassPathResource("static/img").getFile();
		        Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
		        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		        
		   } else {
		        contact.setImage("default.png"); // Or some fallback
		    }
		 
		session.setAttribute("message", new Message("Contact Added Successfully!","success"));
		 
		 String username = principal.getName();
		 User user = userRepository.findByEmail(username);
		 
		 try {
		 int contactId = (int) session.getAttribute("cId");
		 if(contactRepository.existsById(contactId)) {
			 Contact oldContact = contactRepository.findById(contactId).get();
			 user.getContacts().remove(oldContact);
			 
			 String contactImage = contact.getImage();
			 if(!contactImage.equals("default.png")) {
				 File deleteFile = new ClassPathResource("static/img").getFile();
				 File file1 = new File(deleteFile, oldContact.getImage());
				 file1.delete();
			 }
			 contactRepository.deleteById(contactId);
		 }}catch(NullPointerException e) {System.out.println("No attribute in session");}
		 
		 
		 contact.setUser(user);
		 user.getContacts().add(contact);
		 
		 
		 userRepository.save(user);
		}
		catch(Exception e) {
			System.out.print(e.toString());
			session.setAttribute("message", new Message("Unable to add contact, Try again","danger"));
		}
		
		
	}


	@Override
	//@Cacheable(value = "contacts", key = "#user.id + '_' + #page + '_' + #sortField + '_' + #sortDir" )
	public Page<Contact> getContactsWithPaginationAndSorting(int page, String sortField, String sortDir, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findByEmail(username);
		
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(page, 3, sort);
		
		return contactRepository.findByUser(user,pageable);
	}


	@Override
	public Optional<Contact> showContactDetail(Integer cId, Principal principal) {
		
		Optional<Contact> contactOptional = contactRepository.findById(cId);
		if(contactOptional.isPresent()) {
			Contact contact = contactOptional.get();
			
			String username = principal.getName();
			User user = userRepository.findByEmail(username);
			
			if(user.getId()==contact.getUser().getId()) {
				return Optional.of(contact);
			}
		}
		return Optional.empty();
		
	}


	@Override
	public boolean deleteContactService(Integer cId, Principal principal) {
		Optional<Contact> contactOptional = contactRepository.findById(cId);
		if(contactOptional.isPresent()) {
			Contact contact = contactOptional.get();
			
			String username = principal.getName();
			User user = userRepository.findByEmail(username);
			
			if(user.getId()==contact.getUser().getId()) {
				String contactImage = contact.getImage();
				if(!contactImage.equals("default.png")) {
					File deleteFile = null;
					try {
						deleteFile = new ClassPathResource("static/img").getFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					File file1 = new File(deleteFile, contact.getImage());
					file1.delete();
				}
				contact.setUser(null);
				contactRepository.delete(contact);
				return true;
			}
		}
		return false;
		
	}


	@Override
	public Optional<Contact> updateContactService(Integer cId, HttpSession session) {
		Optional<Contact> contact = contactRepository.findById(cId);
		if(contact.isPresent()) {
			return contact;
		}
		else return Optional.empty();
	}


	@Override
	public boolean changePassword(String oldPassword, String newPassword, Principal principal, HttpSession session) {
		String username = principal.getName();
		User user = userRepository.findByEmail(username);
		
		if(bCryptPasswordEncoder.matches(oldPassword, user.getPassword() )) {
			user.setPassword(bCryptPasswordEncoder.encode(newPassword));
			userRepository.save(user);
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public User getUserByemail(Principal principal) {
		String username = principal.getName();
		User user = userRepository.findByEmail(username);
		return user;
	}


	@Override
	public void processUpdateUserService(User user, MultipartFile file) {
		
		try {
			if (!file.isEmpty()) {
		        String fileName = file.getOriginalFilename();
		        // Save the file where needed (filesystem or database)
		        user.setImageUrl(fileName) ;// Save name in DB
		        
		        File saveFile = new ClassPathResource("static/img").getFile();
		        Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
		        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		        
		   }
		   else {
			   user.setImageUrl("default.png");
		   }
			
			userRepository.save(user);
			
		}catch(Exception e){}
		
	}

	
	@Transactional
	@Override
	public void deleteUserService(Principal principal) {
		String username = principal.getName();
		User user = userRepository.findByEmail(username);
		contactRepository.deleteByUser(user);
		userRepository.delete(user);
	}

}

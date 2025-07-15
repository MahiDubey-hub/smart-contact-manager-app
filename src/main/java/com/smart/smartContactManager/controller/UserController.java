package com.smart.smartContactManager.controller;


import java.security.Principal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.smartContactManager.entities.Contact;
import com.smart.smartContactManager.entities.User;
import com.smart.smartContactManager.helper.Message;

import com.smart.smartContactManager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		User user = userService.getUserByemail(principal);
		model.addAttribute("user",user);
	}
	
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Home");
		return "forUser/userDashboard";
	}
	
	@RequestMapping("/addContact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		model.addAttribute("heading","Add Contact");
		return "forUser/addContactForm";
	}
	
	@RequestMapping(value = "/processContact", method = RequestMethod.POST)
	public String processContact(@Valid @ModelAttribute("contact") Contact contact,  BindingResult res,  @RequestParam("img") MultipartFile file, Principal principal, HttpSession session) {
		
		if(res.hasErrors()) {
			session.setAttribute("message", new Message("Unable to add contact, Try again","danger"));
			return "forUser/addContactForm";
		}
		
		userService.addOrUpdateContactService(contact,file,principal,session);
		return "forUser/addContactForm";
	}
	
	
	@GetMapping("/showContacts/{page}")
	public String showContacts(
			@PathVariable("page") Integer page,
			@RequestParam(defaultValue = "name") String sortField,
			@RequestParam(defaultValue = "asc") String sortDir,
			Model  model,
			Principal principal
		) {
		model.addAttribute("title","Show Contacts");
		
		Page<Contact> contacts = userService.getContactsWithPaginationAndSorting(page, sortField, sortDir, principal);
		
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");
		
		return "forUser/showContacts";
	}
	
	@RequestMapping("/{cId}/contact")
	public String showContactDetail(@PathVariable("cId") Integer cId, Model model, Principal principal) {
		
		Optional<Contact> contactOp = userService.showContactDetail(cId, principal);
		if(!contactOp.isEmpty()) {
			Contact contact = contactOp.get();
			model.addAttribute("contact",contact);
			model.addAttribute("title",contact.getName());
		}
		else {
			model.addAttribute("msg","This contact does not exist in your contact list.");
		}
		
		return "forUser/contactDetail";
	}
	
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId, Model model, Principal principal) {
		boolean deleted = userService.deleteContactService(cId, principal);
		if(deleted) {
			model.addAttribute("title", "Deleted Contact");
		}
		else {
			model.addAttribute("title", "Delete Failed");
		}
		return "redirect:/user/showContacts/0";
	}

	@PostMapping("/update/{cId}")
	public String updateContact(@PathVariable("cId") Integer cId,Model model, HttpSession session) {
		 
		Contact contact = userService.updateContactService(cId, session).get();
		
		session.setAttribute("cId",cId);
		
		model.addAttribute("contact",contact);
		model.addAttribute("heading","Update Contact");
		model.addAttribute("title", "Update");
		
		return "forUser/addContactForm";
	}
	
	@RequestMapping("/profile")
	public String profile() {
		return "forUser/profile";
	}
	
	@GetMapping("/settings")
	public String openSettings() {
		return "forUser/settings";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Principal principal, HttpSession session) {
		
		boolean changed = userService.changePassword(oldPassword, newPassword, principal, session);
		
		if(changed) {
			session.setAttribute("message", new Message("Password changed successfully.","alert-success"));
		}
		else {
			session.setAttribute("message", new Message("Incorrect credentials.","alert-danger"));
		}
	
		return "redirect:/user/settings";
	}
	
	@GetMapping("/updateUser")
	public String updateUser(Model model, Principal principal) {
		User user = userService.getUserByemail(principal);
		model.addAttribute("user",user);
		return "forUser/updateUserPage";
	}
	
	@PostMapping("/processUpdateUser")
	public String processUpdateUser(@ModelAttribute User user, @RequestParam("img") MultipartFile file) {
		userService.processUpdateUserService(user, file);
		return "forUser/updateUserPage";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(Principal principal) {
		userService.deleteUserService(principal);
		return "home";
	}
	
	
	
	
	
	
}

package com.smart.smartContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.smartContactManager.entities.User;
import com.smart.smartContactManager.helper.Message;
import com.smart.smartContactManager.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @RequestMapping("/")
    public String home(){
        return "home";
    }
    
    @RequestMapping("/about")
    public String about(Model model) {
    	model.addAttribute("title","About - Smart Contact Manager");
    	return "about";
    }
    
    @RequestMapping("/signup")
    public String signup(Model model) {
    	model.addAttribute("title","SignUp - Smart Contact Manager");
    	model.addAttribute("user",new User());
    	return "signup";
    }
    
    @RequestMapping(value="/do_register", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user,  BindingResult result, @RequestParam(value="agreement", defaultValue="false") boolean agreement, Model model, HttpSession session) {
    	try {
    		if(!agreement) {
    			//throw new Exception("You haven't agreed the terms and conditions");
    			throw new Exception();
    		}
    		if(result.hasErrors()) {
    			model.addAttribute("user",user);
    			return "signup";
    		}
        	user.setRole("ROLE_USER");
        	user.setEnabled(true);
        	user.setPassword(passwordEncoder.encode(user.getPassword()));
        	
        	User savedUser = userRepository.save(user);
        	model.addAttribute("user",savedUser);
        	session.setAttribute("message",new Message("Successfully Registered !!","alert-success"));
    		return "signup";
    	}catch(Exception e){
    		e.printStackTrace();
    		model.addAttribute("user",user);
    		session.setAttribute("message", new Message("Something Went Wrong","alert-danger"));
    		return "signup";
    	}
    	
    }
    
    @RequestMapping("/signin")
    public String login(Model model) {
    	model.addAttribute("title","SignIn - Smart Contact Manager");
    	return "login";
    }

}

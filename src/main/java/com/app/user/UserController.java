package com.app.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	UserRepository userRepo;
	UserService userService;
	
	public UserController(UserRepository userRepo, UserService userService) {
		this.userRepo = userRepo;
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login")
	public String loginUser(@ModelAttribute("user") User user, Model model) {
		if(userRepo.findByEmail(user.getEmail()).isPresent()) {			
			User savedUser = userRepo.findUserByEmail(user.getEmail());
			
			if(!savedUser.isEnabled()) {
				model.addAttribute("notConfirmed", true);
				return "login";
			}
		} 
		model.addAttribute("wrongCredentials", true);
		
		return "login";
	}
}

package com.CollectionTracker.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.CollectionTracker.user.User;
import com.CollectionTracker.user.UserRepository;
import com.CollectionTracker.user.UserService;

@Controller
public class HomeController {
	UserRepository userRepo;
	UserService userService;
	
	@GetMapping("/home")
	public String home(Model model) {
		return "home";
	}
	
	@PostMapping("/home")
	public String loginUser(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("here", true);

		return "home";
	}
}

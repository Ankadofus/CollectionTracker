package com.app.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.user.User;
import com.app.user.UserRepository;
import com.app.user.UserService;

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

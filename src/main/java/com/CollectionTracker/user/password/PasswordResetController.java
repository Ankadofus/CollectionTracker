package com.CollectionTracker.user.password;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.CollectionTracker.user.User;
import com.CollectionTracker.user.UserRepository;

@Controller
public class PasswordResetController {

	UserRepository userRepo;
	PasswordResetService passwordService;
	
	public PasswordResetController(UserRepository UserRepo, PasswordResetService PasswordService) {
		userRepo = UserRepo;
		passwordService = PasswordService;
	}
	
	@GetMapping("/resetPassword")
	public String resetForm(Model model) {
		model.addAttribute("passwordReset", new PasswordReset());
		return "resetPassword";
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute("passwordReset") PasswordReset account, Model model) {		
		//checking if there's an account associated with the email given
		if(!userRepo.findByEmail(account.getEmail()).isPresent()) {
			model.addAttribute("invalidEmail", true);
			return "resetPassword";
		}
		
		User user = userRepo.findUserByEmail(account.getEmail());
		String token = passwordService.getPasswordResetToken(user);
		
		
		passwordService.buildEmail(user.getFirstName(), "http://localhost:3307/changePassword", token);
			
		return "passwordResetSent";
	}
}

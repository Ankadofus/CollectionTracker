package com.app.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.user.User;

@Controller
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	@GetMapping("/registration")
	public String registration(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		return "registration";
	}
	//ToDo add other validator checks
	//Registering a new User
	@PostMapping("/registration")
	public String register(@ModelAttribute("user") User user, Model model) {
		Boolean errorsPresent = false;
		
		//Checking if email is valid
		Boolean isValidEmail = registrationService.emailIsValid(user.getEmail());
		if(!isValidEmail) {
			errorsPresent = true;
			model.addAttribute("invalidEmail", true);
		}
		
		//Checking if email length is valid
		Boolean emailIsValidLength = registrationService.emailIsValidLength(user.getEmail());
		if(!emailIsValidLength) {
			errorsPresent = true;
			model.addAttribute("invalidEmailLength", true);
		}
		
		//Checking if email is already associated with an account
		String emailInUse = registrationService.emailInUse(user.getEmail());
		if(emailInUse.equals("Email already in use!")) {
			errorsPresent = true;
			model.addAttribute("emailInUse", true);
		}
		
		//Checking if email is in use but not confirmed
		if(emailInUse.equals("An account with this Email already exists that requires confirmation")) {
			errorsPresent = true;
			model.addAttribute("unconfirmedEmail", true);
		}
				
		//Checking if password length is valid
		Boolean isValidPassword = registrationService.isPasswordValid(user.getPassword());
		if(!isValidPassword) {
			errorsPresent = true;
			model.addAttribute("invalidPassword", true);
		}
		
		//Checking if First Name is valid
		Boolean isValidFirstName = registrationService.isFirstNameValid(user.getFirstName());
		if(!isValidFirstName) {
			errorsPresent = true;
			model.addAttribute("invalidFirstName", true);
		}
		
		//Checking if Last Name is valid
		Boolean isValidLastName = registrationService.isLastNameValid(user.getLastName());
		if(!isValidLastName) {
			errorsPresent = true;
			model.addAttribute("invalidLastName", true);
		}
		
		//Checking if Username is valid
		Boolean isValidUsername = registrationService.isUserNameValid(user.getUsername());
		if(!isValidUsername) {
			errorsPresent = true;
			model.addAttribute("invalidUsername", true);
		}
		
		if(errorsPresent) {
			return "registration";
		}
		
		RegistrationRequest request = 
				new RegistrationRequest(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword());
		
		registrationService.register(request);
		
		return "/successfulRegistration";
	}
	
	@GetMapping(path = "/registration/confirm")
	public String confirm(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}
	
}

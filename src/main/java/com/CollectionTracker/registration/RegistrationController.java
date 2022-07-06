package com.CollectionTracker.registration;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CollectionTracker.registration.token.ConfirmationToken;
import com.CollectionTracker.registration.token.ConfirmationTokenService;
import com.CollectionTracker.user.User;

@Controller
public class RegistrationController {
	
	private final RegistrationService registrationService;
	private final ConfirmationTokenService confirmationTokenService;
	
	public RegistrationController(RegistrationService registrationService, ConfirmationTokenService confirmationTokenService) {
		this.registrationService = registrationService;
		this.confirmationTokenService = confirmationTokenService;
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
				
		//Checking if password input was empty
		if(user.getPassword().equals("")) {
			errorsPresent = true;
			model.addAttribute("noPassword", true);
		}
		
		//Checking if password length is valid
		Boolean isValidPassword = registrationService.isPasswordValid(user.getPassword());
		if(!isValidPassword) {
			errorsPresent = true;
			model.addAttribute("invalidPassword", true);
		}
		
		//Checking if first name input was empty
		if(user.getFirstName().equals("")) {
			errorsPresent = true;
			model.addAttribute("noFirstName", true);
		}
				
		//Checking if First Name is valid
		Boolean isValidFirstName = registrationService.isFirstNameValid(user.getFirstName());
		if(!isValidFirstName) {
			errorsPresent = true;
			model.addAttribute("invalidFirstName", true);
		}
		
		//Checking if first name input was empty
		if(user.getLastName().equals("")) {
			errorsPresent = true;
			model.addAttribute("noLastName", true);
		}
				
		//Checking if Last Name is valid
		Boolean isValidLastName = registrationService.isLastNameValid(user.getLastName());
		if(!isValidLastName) {
			errorsPresent = true;
			model.addAttribute("invalidLastName", true);
		}
		
		//Checking if first name input was empty
		if(user.getUsername().equals("")) {
			errorsPresent = true;
			model.addAttribute("noUsername", true);
		}		
		
		//Checking if Username is valid
		Boolean isValidUsername = registrationService.isUserNameValid(user.getUsername());
		if(!isValidUsername) {
			errorsPresent = true;
			model.addAttribute("invalidUsername", true);
		}
		
		RegistrationRequest request = 
				new RegistrationRequest(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword());
		
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
		if(emailInUse.equals("An account with this Email already exists that requires confirmation. Check your email")) {
			errorsPresent = true;
			model.addAttribute("unconfirmedEmail", true);
			User savedUser = registrationService.savedUser(user);
			
			
			String token = UUID.randomUUID().toString();
			ConfirmationToken conToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), savedUser);
			confirmationTokenService.saveConfirmationToken(conToken);
			
			registrationService.sendConfimationToken(token, request);
		}
		
		if(errorsPresent) {
			return "registration";
		}
	
		registrationService.register(request);
		
		return "/successfulRegistration";
	}
	
	@GetMapping(path = "registration/confirm")
	public String confirm(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}
	
}

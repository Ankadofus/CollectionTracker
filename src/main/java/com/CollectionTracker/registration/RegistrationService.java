package com.CollectionTracker.registration;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.CollectionTracker.email.EmailSender;
import com.CollectionTracker.registration.token.ConfirmationToken;
import com.CollectionTracker.registration.token.ConfirmationTokenService;
import com.CollectionTracker.user.User;
import com.CollectionTracker.user.UserRole;
import com.CollectionTracker.user.UserService;

@Service
public class RegistrationService {
	
	private final EmailValidator emailValidator;
	private final UserService userService;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailSender emailSender;
	
	public RegistrationService(EmailValidator emailValidator, UserService userService, 
			ConfirmationTokenService confirmationTokenService, EmailSender emailSender){
		this.emailValidator = emailValidator;
		this.userService = userService;
		this.confirmationTokenService = confirmationTokenService;
		this.emailSender = emailSender;
	}
	
	public String register(RegistrationRequest request) {		
		String token = userService.signUpUser(new User(
						request.getFirstname(), 
						request.getLastname(),
						request.getUsername(),
						request.getEmail(), 
						request.getPassword(),
						UserRole.USER));
		
		sendConfimationToken(token, request);
		
		return token;
	}
	
	public User savedUser(User user) {
		return userService.savedUser(user.getEmail());
	}
	
	public String emailInUse(String email) {
		return userService.emailInUse(email);
	}
	
	public Boolean emailIsValid(String email) {
		if(emailValidator.test(email)) {
			return true;
		}		
		return false;
	}
	
	public Boolean isPasswordValid(String password) {
		if(password.length() <= 60) {
			return true;
		}
		return false;
	}
	
	public Boolean isFirstNameValid(String firstName) {
		if(firstName.length() <= 20) {
			return true;
		}
		return false;
	}
	
	public Boolean isLastNameValid(String lastName) {
		if(lastName.length() <= 25) {
			return true;
		}
		return false;
	}
	
	public Boolean isUserNameValid(String username) {
		if(username.length() <= 20) {
			return true;
		}
		return false;
	}
	
	public Boolean emailIsValidLength(String username) {
		if(username.length() <= 40) {
			return true;
		}
		return false;
	}
	
	public void sendConfimationToken(String token, RegistrationRequest request) {
		String link = "http://localhost:3307/registration/confirm?token=" + token;
		emailSender.send(request.getEmail(), userService.buildEmail(request.getFirstname(), link));
	}
	
	@Transactional
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("Token not found."));
		
		if(confirmationToken.getComfirmedAt() != null) {
			throw new IllegalStateException("Email is already confirmed.");
		}
		
		LocalDateTime expiresAt = confirmationToken.getExpiresAt();
		
		if(expiresAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("Token has expired.");
		}
		
		confirmationTokenService.setConfirmedAt(token);
		userService.enableUser(confirmationToken.getUser().getEmail());
		
		return "accountConfirmed";
	}
}

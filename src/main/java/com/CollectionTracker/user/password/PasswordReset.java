package com.CollectionTracker.user.password;

import com.CollectionTracker.user.password.token.PasswordResetToken;

public class PasswordReset {

	String email;
	PasswordResetToken token;

	public PasswordReset() {}
	
	public PasswordReset(String email, PasswordResetToken passwordToken) {
		this.token = passwordToken;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
}

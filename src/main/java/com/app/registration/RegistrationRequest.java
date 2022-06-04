package com.app.registration;

import java.util.Objects;

public class RegistrationRequest {

	private final String firstName;
	private final String lastName;
	private final String userName;
	private final String email;
	private final String password;
	
	public RegistrationRequest(String firstname, String lastname, String username, String email, String password) {
		this.firstName = firstname;
		this.lastName = lastname;
		this.userName = username;
		this.email = email;
		this.password = password;
	}

	public final String getFirstname() {
		return firstName;
	}

	public final String getLastname() {
		return lastName;
	}
	
	public final String getUsername() {
		return userName;
	}

	public final String getEmail() {
		return email;
	}

	public final String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "RegistrationRequest [firstname=" + firstName + ", lastname=" + lastName + " username=" + userName+
				", email=" + email+ ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationRequest other = (RegistrationRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && 
				Objects.equals(userName, other.userName)&& Objects.equals(lastName, other.lastName) && 
				Objects.equals(password, other.password);
	}
	
	
	
	
}

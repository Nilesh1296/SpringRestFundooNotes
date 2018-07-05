package com.bridgeit.fundoonotes.user.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


public class Login
{
 @Email
private String email;
 
 @NotEmpty(message = "Please enter your password.")
 @Size(min = 6, max = 60, message = "Your password must between 6 and 15 characters")
private String password;
 
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
}

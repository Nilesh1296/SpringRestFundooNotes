package com.bridgeit.fundoonotes.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundoonotes.user.model.Login;
import com.bridgeit.fundoonotes.user.model.User;
import com.bridgeit.fundoonotes.user.service.UserService;



@RestController
public class RegistrationController {
	
	@Autowired
	private UserService userservice;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody User user) {
		
	   int userid = userservice.save(user);
		if(userid>0)
		{
			return new ResponseEntity<String>("New Person has been saved with ID:", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Already emailid exist", HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/save/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> get(@PathVariable("id") int id) {
		
		User user = userservice.get(id);
		if (user == null) 
		{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody Login login) 
	{
		boolean EmailExist = userservice.checkUserByEmail(login.getEmail(),login.getPassword());
         
		if (EmailExist) {
			return new ResponseEntity<String>("login success:", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Either wrong email or password:", HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/verifyaccount/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> verifyAccount(@PathVariable String token) 
	{
		System.out.println("controller token"+token);
         System.out.println("controller"+userservice.isVerified(token));
		if (userservice.isVerified(token)) {
			
			return new ResponseEntity<String>("verification success:", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Verification not successfully:", HttpStatus.CONFLICT);
		}
	}
	@RequestMapping(value ="/forgetpassword",method = RequestMethod.POST)
	public ResponseEntity<String> forgetPassword(@RequestBody Login login,HttpServletRequest request)
	{
		String url = "http://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath()+"/resetpassword/";
		System.out.println("controller "+url);
		String email=login.getEmail();
		System.out.println(email);
		boolean status = userservice.forgetPassword(email, url);
		if(status)
		{
			return new ResponseEntity<String>("Link Sended:", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Link Not Sended:", HttpStatus.OK);
		}
		
}
	
	@RequestMapping(value ="/resetpassword/{token:.+}",method = RequestMethod.PUT)
	public ResponseEntity<String> forgetPassword(@PathVariable("token") String token,@RequestBody Login login)
	{
		//String password =null;
		System.out.println("Inside controller");
		String password = login.getPassword();
		if(userservice.resetPassword(token, password))
		{
			System.out.println("INSIDE CONTROLLER IF ");
			return new ResponseEntity<String>("Password resetet:", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Password Not resetet:", HttpStatus.OK);
		}
		
		
	}
	
}

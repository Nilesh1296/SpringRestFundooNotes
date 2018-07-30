package com.bridgeit.fundoonotes.user.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.bridgeit.fundoonotes.user.model.User;

public interface UserService {
	
	public int save(User user);

	public User get(int id);

	public String checkUserByEmail(String email, String password);

	public boolean isVerified(String token);

	boolean forgetPassword(String email, String url,HttpServletResponse res) throws IOException;
    
	boolean resetPassword(String token,String password);
}

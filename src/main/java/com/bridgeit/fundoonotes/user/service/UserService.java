package com.bridgeit.fundoonotes.user.service;

import com.bridgeit.fundoonotes.user.model.User;

public interface UserService {
	public int save(User user);

	public User get(int id);

	public boolean checkUserByEmail(String email, String password);

	public boolean isVerified(String token);

	boolean forgetPassword(String email, String url);
    
	boolean resetPassword(String token,String password);
}

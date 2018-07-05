package com.bridgeit.fundoonotes.user.dao;

import com.bridgeit.fundoonotes.user.model.User;

// method should be combined
public interface UserDao
{
 public int save(User user);
 public User get(int id);
 public User check(String name,String password);
 public boolean checkPass(String Password, String hashedPassword);
 public boolean getUniqueEmail(String email);
 public boolean update(User user);
 public User getEmail(String email);
}

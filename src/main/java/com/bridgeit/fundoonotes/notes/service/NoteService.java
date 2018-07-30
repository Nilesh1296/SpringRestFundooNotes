package com.bridgeit.fundoonotes.notes.service;

import java.util.List;

import com.bridgeit.fundoonotes.notes.model.Note;


public interface NoteService 
{
  public Note save(String token,Note note);
  public boolean delete(String token,int id);
  public List<Note> getUser(String token);
  public boolean update(String token,Note note);
 
}

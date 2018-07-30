package com.bridgeit.fundoonotes.notes.dao;

import java.util.List;

import com.bridgeit.fundoonotes.notes.model.Note;
import com.bridgeit.fundoonotes.user.model.User;

public interface NoteDao
{
	public int save(Note note);
	public int delete(Note note);
	public Note getEmail(String email);
	public Note get(int idNote);
	public List<Note> getUser(User user);
	public Note update(Note note);
	
}

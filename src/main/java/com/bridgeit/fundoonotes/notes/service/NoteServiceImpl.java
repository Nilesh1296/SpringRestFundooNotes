package com.bridgeit.fundoonotes.notes.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.fundoonotes.notes.dao.NoteDao;
import com.bridgeit.fundoonotes.notes.model.Note;
import com.bridgeit.fundoonotes.user.dao.UserDao;
import com.bridgeit.fundoonotes.user.model.User;
import com.bridgeit.fundoonotes.user.tokengenerator.TokenDecoder;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	private UserDao userdao;

	@Autowired
	private NoteDao notedao;

	@Override
	public Note save(String token, Note note) {
		Date date = new Date();
		long id = TokenDecoder.parseJWT(token);
		int i = (int) id;
		User user = userdao.get(i);
		System.out.println(user);
		note.setUser(user);
		System.out.println(note);
		note.setCreateddate(date);
		notedao.save(note);

		return note;
	}

	@Override
	public boolean delete(String token, int id) {

		long tokenid = TokenDecoder.parseJWT(token);
		int i = (int)tokenid;
		User user = userdao.get(i);
		Note note = notedao.get(id);
	//	System.out.println(note.getUser());
		if ((note.getUser().getId()) == user.getId()) {
			notedao.delete(note);
			return true;
		}
		return false;
	}

	@Override
	public List<Note> getUser(String token) {
		long id = TokenDecoder.parseJWT(token);
		int i = (int) id;
		User user = userdao.get(i);
		List<Note> list = notedao.getUser(user);

		return list;
	}

	@Override
	public boolean update(String token,Note note) {

		long id = TokenDecoder.parseJWT(token);
		int userid = (int) id;
		System.out.println(id);
		User user = userdao.get(userid);

		Note note2 = notedao.get(note.getId());
		System.out.println(note2.getTitle());
		notedao.update(note2);
		System.out.println(note2.getUser().getId());
		if ((note2.getUser().getId()) == userid) {
			Date date = new Date();
			System.out.println(note2);
			System.out.println("inside if");
			note2.setTitle(note.getTitle());
			note2.setTrash(note.isTrash());
			note2.setDescription(note.getDescription());
			note2.setModifiedDate(date);
			note2.setPin(note.isPin());
			note2.setColour(note.getColour());
			note2.setArchive(note.isArchive());
			note2.setCreateddate(date);
			notedao.update(note2);
			System.out.println(note2.getTitle());
			return true;
		}
		return false;
	}

}

package com.bridgeit.fundoonotes.notes.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bridgeit.fundoonotes.notes.model.Note;
import com.bridgeit.fundoonotes.user.model.User;


@Repository
public class NoteDaoImpl implements NoteDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int save(Note note) {
		
		sessionFactory.getCurrentSession().save(note);
		return note.getId();
	}

	@Override
	public int delete(Note note) {
		
		sessionFactory.getCurrentSession().delete(note);
		return note.getId();
	}

	@Override
	public Note getEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note get(int idNote) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Note.class)
				.add(Restrictions.eq("idNote",idNote));
		return (Note) criteria.uniqueResult();
		
	}

	@Override
	public List<Note> getUser(User user) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Note.class)
				.add(Restrictions.eq("user",user));
		List<Note> note = criteria.list();
		return note;
	}

	@Override
	public Note update(Note note) {
	System.out.println("hi in dao");
		System.out.println(note.getTitle());
		sessionFactory.getCurrentSession().update(note);
		return note;
	}
	
	
	}


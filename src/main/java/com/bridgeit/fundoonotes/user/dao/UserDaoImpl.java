package com.bridgeit.fundoonotes.user.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundoonotes.user.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class.getName());
	User user = null;

	@Autowired
	private SessionFactory sessionFactory;

	@Override

	public int save(User user) {
		// Service
		String password = user.getPassword();
		String hashed = BCrypt.hashpw(password.toString(), BCrypt.gensalt(12));
		user.setPassword(hashed);
		sessionFactory.getCurrentSession().save(user);
		return user.getId();
	}

	@Override
	public User get(int id) {

		return sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public User check(String email, String password) {
		@SuppressWarnings("deprecation")
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email));

		List<User> user = criteria.list();
		return user.size() > 0 ? user.get(0) : null;

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean getUniqueEmail(String email) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email));
		user = (User) criteria.uniqueResult();
		if (user == null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean update(User user) {

		sessionFactory.getCurrentSession().update(user);

		return true;

	}

	@Override
	public boolean checkPass(String Password, String hashedPassword) {
		if (BCrypt.checkpw(Password, hashedPassword)) {

			return true;

		} else {
			return false;
		}
	}

	@Override
	public User getEmail(String email) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email));

		List<User> user = criteria.list();
		return user.size() > 0 ? user.get(0) : null;

	}

}

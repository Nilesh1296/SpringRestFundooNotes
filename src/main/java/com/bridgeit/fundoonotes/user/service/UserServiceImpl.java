package com.bridgeit.fundoonotes.user.service;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.fundoonotes.user.dao.UserDao;
import com.bridgeit.fundoonotes.user.mailsender.MailSender;
import com.bridgeit.fundoonotes.user.model.User;
import com.bridgeit.fundoonotes.user.tokengenerator.TokenDecoder;
import com.bridgeit.fundoonotes.user.tokengenerator.TokenGenerator;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

@Service
@Transactional

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userdao;
	User user;

	MailSender mailsender = new MailSender();
	Session session = mailsender.getsession();

	@Override
	public int save(User user) {
	 boolean status = userdao.getUniqueEmail(user.getEmail());
		System.out.println(status);
	if (status) {
			String email = user.getEmail();
			String subject = "Registration successful";
			String body = "Please generate your token ";

			TokenGenerator tokengenerator = new TokenGenerator();
			int id = userdao.save(user);
			System.out.println(id);
			String tokenid = id +"";
			String issuer = "Shiv";
			String subject1 = "Ram";
			long ttlMillis = 10000000;
			String token = TokenGenerator.createJWT(tokenid, issuer, subject1, ttlMillis);
			String tokenurl = "http://localhost:8090/Fundoonotes/verifyaccount/"+token;
			MailSender.sendEmail(session, email, subject,tokenurl);
			int idkey = user.getId();
			RedisClient redisClient = RedisClient.create("redis://@localhost:6379/");
			StatefulRedisConnection<String, String> connection = redisClient.connect();
			RedisCommands<String, String> syncCommands = connection.sync();
			syncCommands.set(id + "", token);
			return id;
		} else {
			System.out.println("Already exist");
			return 0;
		}
	}

	@Override
	public User get(int id) {
		return userdao.get(id);
	}

	@Override
	public String checkUserByEmail(String email,String password) {
		User user = userdao.check(email,password);
		int id = user.getId();
	    String hashedpassword =	user.getPassword();
	   /* RedisClient redisClient = RedisClient.create("redis://@localhost:6379/");
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> syncCommands = connection.sync();*/
		String token = TokenGenerator.createJWT(id + "", "forgetpassword", "resetpassword", 1000000);
		System.out.println(token);
		//syncCommands.set(id+"", token);
		
		if (user!= null&&userdao.checkPass(password, hashedpassword)){

			return token;
		} 
		return null;
		}
	


	@Override
	public boolean isVerified(String token) {
		int id = TokenDecoder.parseJWT(token);
		User user = userdao.get(id);

		RedisClient redisClient = RedisClient.create("redis://@localhost:6379/");
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> syncCommands = connection.sync();
		System.out.println(id);
		String tokenid = syncCommands.get(id + "");
        
		if (tokenid.equals(token)) {
			System.out.println("inside if ");
			System.out.println("user object " + user);

			user.setActive(true);
			boolean flag = userdao.update(user);
			System.out.println("after if" + flag);
			return true;
		} else {
			System.out.println("token not verified");
			return false;

		}

	}

	@Override
	public boolean forgetPassword(String email, String url,HttpServletResponse res) throws IOException {
		User user = userdao.getEmail(email);
		System.out.println(user);
		if (user != null)
		{
           System.out.println("Inside if");
			int id = user.getId();
			String token = TokenGenerator.createJWT(id + "", "forgetpassword", "resetpassword", 1000000);
			//String tokenurl = url + token;
			String tokenurl="http://127.0.0.1:3000/#!/resetpassword?token="+token;
			res.sendRedirect("http://127.0.0.1:3000/#!/resetpassword?token="+token);
			MailSender.sendEmail(session, email, "forgetpassword", tokenurl);
			RedisClient redisClient = RedisClient.create("redis://@localhost:6379/");
			res.sendRedirect("http://127.0.0.1:3000/#!/resetpassword?token="+token);
			StatefulRedisConnection<String, String> connection = redisClient.connect();
			RedisCommands<String, String> syncCommands = connection.sync();
			syncCommands.set(id + "", token);
		//	http://127.0.0.1:3000/#!/resetpassword
			return true;
		} else {
			System.out.println("else");
			return false;
		}
	}

	@Override
	public boolean resetPassword(String token,String password)
	{
		System.out.println("service");
		int id = TokenDecoder.parseJWT(token);
		User user = userdao.get(id);
		RedisClient redisClient = RedisClient.create("redis://@localhost:6379/");
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> syncCommands = connection.sync();
		String tokenid = syncCommands.get(id+"");
		//boolean status = user.isActive();
		if(tokenid.equals(token))
		{
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			user.setPassword(hashed);
			userdao.update(user);
			return true;
		}
		else
		{
		return false;
		}
	}

	
	
	
}

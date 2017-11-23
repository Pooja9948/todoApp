package com.bridgelabz.User.DAO;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.OTPDetails;
import com.bridgelabz.Util.token.Token;

public class UserDAOImpl implements UserDAO {

	/**
	 * @param userDetails
	 * @return inserting all the data of user to the database
	 */
	public  static Logger logger = Logger.getLogger(UserDAO.class);
	
	@Autowired
	SessionFactory sessionFactory;

	private static final String key = "Token";
	
	@Autowired
	private RedisTemplate<String, Object> template;

	private HashOperations<String, String, Token> hashops;

	public int registration(UserDetails userDetails) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int id = (int) session.save(userDetails);
			transaction.commit();
			return id;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			try {
				if (session != null)
					session.close();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	public UserDetails login(UserDetails userDetails) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", userDetails.getEmail()));
		criteria.add(Restrictions.eq("password", userDetails.getPassword()));
		criteria.add(Restrictions.eq("isActivated", true));
		UserDetails user = (UserDetails) criteria.uniqueResult();

		if (user == null) {
			return null;

		}
		return user;
	}
	/*
	 * @Override public boolean emailValidation(String email) { Session session
	 * = sessionFactory.openSession();
	 * 
	 * @SuppressWarnings("deprecation") Criteria criteria =
	 * session.createCriteria(UserDetails.class).add(Restrictions.eq("email",
	 * email));
	 * 
	 * @SuppressWarnings("unused") boolean res=(boolean)
	 * criteria.uniqueResult(); session.close(); if(res) return true; else
	 * return false; }
	 */

	public UserDetails emailValidation(String email) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class).add(Restrictions.eq("email", email));
		UserDetails user = (UserDetails) criteria.uniqueResult();
		session.close();
		return user;
	}

	/*FOR REDIS IMPLEMENTATION*/
	@Override
	public void saveTokenInRedis(Token token) {
		System.out.println("token in dao " + token);
		hashops = template.opsForHash();
		System.out.println("hashops : " + hashops);
		hashops.put(key, token.getGenerateToken(), token);
		System.out.println("is this null " + hashops.get(key, token.getGenerateToken()));
	}

	@Override
	public Token getToken(String token) {
		hashops = template.opsForHash();
		Token token2 = hashops.get(key, token);
		System.out.println("get token " + token2);
		return token2;

	}

	public UserDetails getUserById(int id) {
		Session session = sessionFactory.openSession();
		UserDetails userDetails = session.get(UserDetails.class, id);
		System.out.println("User by id is: " + userDetails);
		session.close();
		return userDetails;
	}

	public boolean updateUserPassword(UserDetails userDetails) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		System.out.println("email in updateUser : " + userDetails.getEmail() + "password in updateUser :"
				+ userDetails.getPassword());
		String email = userDetails.getEmail();
		String password = userDetails.getPassword();
		try {
			transaction = session.beginTransaction();
			// session.saveOrUpdate(userDetails);
			Query updateUser = session
					.createQuery("update UserDetails set password = :password1" + " where email=:email1");
			updateUser.setParameter("password1", password);
			updateUser.setParameter("email1", email);

			updateUser.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
				session.close();
				return false;
			}
		}
		session.close();
		return true;
	}

	@Override
	public UserDetails getUserByEmail(String email) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", email));
		UserDetails user = (UserDetails) criteria.uniqueResult();
		session.close();
		return user;
		
		
	}

	@Override
	public boolean updateUser(UserDetails user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
				session.close();
				return false;
			}
		}
		session.close();
		return true;
	}

	@Override
	public void saveOTP(OTPDetails otpDetails) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int id = (int) session.save(otpDetails);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (session != null)
					session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
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

/**
 * @author Pooja todoApp
 *
 */
public class UserDAOImpl implements UserDAO {

	/**
	 * @param userDetails
	 * @return inserting all the data of user to the database
	 */
	public static Logger logger = Logger.getLogger(UserDAOImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	private static final String key = "Token";

	@Autowired
	private RedisTemplate<String, Object> template;

	private HashOperations<String, String, Token> hashops;

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#registration(com.bridgelabz.User.model.UserDetails)
	 * register the user
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#login(com.bridgelabz.User.model.UserDetails)
	 * check the user is valid or not at the time of login
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#emailValidation(java.lang.String)
	 * check the emial id is already exist or not
	 */
	public UserDetails emailValidation(String email) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class).add(Restrictions.eq("email", email));
		UserDetails user = (UserDetails) criteria.uniqueResult();
		session.close();
		return user;
	}

	/* FOR REDIS IMPLEMENTATION */
	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#saveTokenInRedis(com.bridgelabz.Util.token.Token)
	 * save the token in redis
	 */
	@Override
	public void saveTokenInRedis(Token token) {
		System.out.println("token in dao " + token);
		hashops = template.opsForHash();
		System.out.println("hashops : " + hashops);
		hashops.put(key, token.getGenerateToken(), token);
		System.out.println("is this null " + hashops.get(key, token.getGenerateToken()));
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#getToken(java.lang.String)
	 * get the token from redis
	 */
	@Override
	public Token getToken(String token) {
		hashops = template.opsForHash();
		Token token2 = hashops.get(key, token);
		System.out.println("get token " + token2);
		return token2;

	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#getUserById(int)
	 * get the user by id
	 */
	public UserDetails getUserById(int id) {
		Session session = sessionFactory.openSession();
		UserDetails userDetails = session.get(UserDetails.class, id);
		System.out.println("User by id is: " + userDetails);
		session.close();
		return userDetails;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#updateUserPassword(com.bridgelabz.User.model.UserDetails)
	 * update the user password
	 */
	public boolean updateUserPassword(UserDetails userDetails) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		System.out.println("id in updateUser : " + userDetails.getId() + "password in updateUser :"
				+ userDetails.getPassword());
		int id = userDetails.getId();
		String password = userDetails.getPassword();
		try {
			transaction = session.beginTransaction();
			// session.saveOrUpdate(userDetails);
			Query updateUser = session
					.createQuery("update UserDetails set password = :password1" + " where userId=:id");
			updateUser.setParameter("password1", password);
			updateUser.setParameter("id", id);

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

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#getUserByEmail(java.lang.String)
	 * get the user by the email id 
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#updateUser(com.bridgelabz.User.model.UserDetails)
	 * update the user
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#saveOTP(com.bridgelabz.Util.OTPDetails)
	 * save otp
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.DAO.UserDAO#userValidated(int)
	 * check the user id valid or not
	 */
	public UserDetails userValidated(int UserId) {
		Session session = this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(UserDetails.class).add(Restrictions.eq("id", UserId));
		UserDetails user = (UserDetails) criteria.uniqueResult();
		session.close();
		return user;

	}

}

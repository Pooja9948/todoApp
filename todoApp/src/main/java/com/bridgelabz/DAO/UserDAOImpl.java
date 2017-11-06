package com.bridgelabz.DAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.bridgelabz.model.Token;
//import com.bridgeit.SingleTon.SingleTon;
import com.bridgelabz.model.UserDetails;

public class UserDAOImpl implements UserDAO{

	/**
	 * @param userDetails
	 * @return
	 * inserting all the data of user to the database
	 */
	@Autowired
	SessionFactory sessionFactory;

	private static final String key="Token";

	@Autowired
	private RedisTemplate<String, Object> template;

	private HashOperations<String, String, Token> hashops;

	public void registration(UserDetails userDetails) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(userDetails);
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
	public UserDetails login(UserDetails userDetails){
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", userDetails.getEmail()));
		criteria.add(Restrictions.eq("password", userDetails.getPassword()));
		UserDetails user=(UserDetails) criteria.uniqueResult();

		if(user==null)
		{
			return null;

		}
		return user;
	}
	/*	@Override
	public boolean emailValidation(String email) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class).add(Restrictions.eq("email", email));
		@SuppressWarnings("unused")
		boolean res=(boolean) criteria.uniqueResult();
		session.close();
		if(res)
			return true;
		else
			return false;
	}*/


	public UserDetails emailValidation(String email) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class).add(Restrictions.eq("email", email));
		UserDetails user = (UserDetails) criteria.uniqueResult();
		session.close();
		return user;
	}
	@Override
	public void saveTokenInRedis(Token token) {
		hashops = template.opsForHash();
		hashops.put(key, token.getGenerateToken(), token);
		System.out.println("is this null " +hashops.get(key, token.getGenerateToken()));


	}

	@Override
	public Token getToken(String token) {
		hashops = template.opsForHash();
		Token token2 = hashops.get(key, token);
		return token2;

	}

}

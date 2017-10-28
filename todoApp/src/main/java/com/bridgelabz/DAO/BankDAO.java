package com.bridgelabz.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

//import com.bridgeit.SingleTon.SingleTon;
import com.bridgelabz.model.UserDetails;

public class BankDAO {

	/**
	 * @param email
	 * @param password
	 * @return
	 * checking the email and password 
	 */
	/*public static UserDetails login(String email, String password) {
		SessionFactory sessionFactory = SingleTon.getSF();
		Session session = sessionFactory.openSession();
		int status = 0;
		Criteria criteria = session.createCriteria(UserDetails.class);
		Criterion email1 = Restrictions.eq("email", email);
		Criterion password1 = Restrictions.eq("password", password);
		LogicalExpression andExp = Restrictions.and(email1, password1);
		criteria.add(andExp);
		UserDetails userDetails = null;
		List result = criteria.list();
		Iterator iterator = result.iterator();
		String name = "";
		int id = 0;
		while (iterator.hasNext()) {
			userDetails = (UserDetails) iterator.next();
			System.out.println("user name is--> " + userDetails.getName());
			name = userDetails.getName();
			System.out.println("user id is--> " + userDetails.getId());
			id = userDetails.getId();
		}
		session.close();
		return userDetails;

	}*/

	/**
	 * @param userDetails
	 * @return
	 * inserting all the data of user to the database
	 */
	@Autowired
	SessionFactory sessionFactory;
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

}

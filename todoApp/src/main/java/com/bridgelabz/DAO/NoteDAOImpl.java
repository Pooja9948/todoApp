package com.bridgelabz.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.model.NoteDetails;

public class NoteDAOImpl implements NoteDAO{
	@Autowired
	SessionFactory sessionfactory;

	public void createNote(NoteDetails note) {
		Session session = sessionfactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(note);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
}

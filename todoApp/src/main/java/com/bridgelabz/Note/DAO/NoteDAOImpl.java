package com.bridgelabz.Note.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.User.Controller.UserController;
import com.bridgelabz.User.model.UserDetails;

public class NoteDAOImpl implements NoteDAO {
	
	public  static Logger logger = Logger.getLogger(NoteDAOImpl.class);
	@Autowired
	SessionFactory sessionfactory;
	Transaction transaction = null;

	public void createNote(NoteDetails note) {
		Session session = sessionfactory.openSession();

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

	public void updateNote(NoteDetails noteDetails) {
		Session session = sessionfactory.openSession();

		try {
			transaction = session.beginTransaction();
			Date date = new Date();
			noteDetails.setModifiedDate(date);
			session.update(noteDetails);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public void deleteNote(int noteId) {
		Session session = sessionfactory.openSession();

		System.out.println("jhdbvj " + noteId);
		try {
			transaction = session.beginTransaction();
			Query deleteNote = session.createQuery("delete from NoteDetails where id=:noteId");
			deleteNote.setParameter("noteId", noteId);

			deleteNote.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public NoteDetails getNoteById(int noteId) {
		Session session = sessionfactory.openSession();
		NoteDetails note = session.get(NoteDetails.class, noteId);
		return note;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@Override
	public List<NoteDetails> getAllNotes(UserDetails userDetails) {
		Session session = sessionfactory.openSession();

		System.out.println("ytdyqwe wedfuwe weufdwebfmw u");
		int uid = userDetails.getId();
		UserDetails user = session.get(UserDetails.class, uid);		
		Criteria criteria = session.createCriteria(NoteDetails.class);
		criteria.add(Restrictions.eq("userDetails", user));
		criteria.addOrder(Order.desc("modifiedDate"));
		List notes = criteria.list();
		//logger.trace(criteria.list());
		
		return notes;
	}

}

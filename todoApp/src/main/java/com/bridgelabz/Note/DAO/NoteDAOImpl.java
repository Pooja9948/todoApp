package com.bridgelabz.Note.DAO;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.query.Query;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Note.model.NoteCollaborate;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.User.Controller.UserController;
import com.bridgelabz.User.model.UserDetails;

public class NoteDAOImpl implements NoteDAO {

	public static Logger logger = Logger.getLogger(NoteDAOImpl.class);
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

		System.out.println("user id ---+++++-----++++ " + userDetails.getId());
		int uid = userDetails.getId();
		
		 /*Query getNote = session.createQuery(
		 "from NoteDetails where user_detailid=:uid or user_detailid IN (select ownerId_user_detailid from NoteCollaborate where shareId_user_detailid =:uid ) order by modifiedDate desc");
		 getNote.setParameter("uid", uid);*/
		 
		 
		// Query getCollaborateNote = session.createQuery("");
		// getCollaborateNote.setParameter("uid", uid);

		UserDetails user = session.get(UserDetails.class, uid);
		Criteria criteria = session.createCriteria(NoteDetails.class);
		criteria.add(Restrictions.eq("userDetails", user));
		criteria.addOrder(Order.desc("modifiedDate"));
		List notes = criteria.list();
		
		//List notes = getNote.list();
		// logger.trace(criteria.list());
		//logger.info(getNote.list());
		return notes;
	}

	public void deleteScheduleNote() {
		Session session = sessionfactory.openSession();

		System.out.println("jhdbvj ");
		// Date currentdate=new Date();
		Date deleteTime = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
		boolean trash = true;
		try {
			transaction = session.beginTransaction();
			Query deleteNote = session
					.createQuery("delete from NoteDetails where modifiedDate<:deleteTime and isTrash=:trash");
			deleteNote.setParameter("deleteTime", deleteTime);
			deleteNote.setParameter("trash", trash);

			int count = deleteNote.executeUpdate();
			System.out.println("Number of notes deleted: " + count);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public void deleteAllNote() {
		Session session = sessionfactory.openSession();

		boolean trash = true;
		try {
			transaction = session.beginTransaction();
			Query deleteNote = session.createQuery("delete from NoteDetails where  isTrash=:trash");
			deleteNote.setParameter("trash", trash);

			int count = deleteNote.executeUpdate();
			System.out.println("Number of notes deleted: " + count);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public int saveCollborator(NoteCollaborate collborate) {
		int collboratorId = 0;
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			collboratorId = (Integer) session.save(collborate);
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return collboratorId;
	}

	public List<UserDetails> getListOfUser(int noteId) {

		Session session = sessionfactory.openSession();
		Query querycollab = session.createQuery("select c.shareId from NoteCollaborate c where c.noteId= " + noteId);
		List<UserDetails> listOfSharedCollaborators = querycollab.list();
		System.out.println("listOfSharedCollaborators " + listOfSharedCollaborators);
		session.close();
		return listOfSharedCollaborators;
	}

	public List<NoteDetails> getCollboratedNotes(int userId) {
		Session session = sessionfactory.openSession();
		Query query = session.createQuery("select c.noteId from NoteCollaborate c where c.shareId= " + userId);
		List<NoteDetails> colllboratedNotes = query.list();
		//Set<NoteDetails> notes = new HashSet<NoteDetails>(colllboratedNotes);

		session.close();
		return colllboratedNotes;
	}

	public int removeCollborator(int shareWith, int noteId) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session
				.createQuery("delete  NoteCollaborate c where c.shareId= " + shareWith + " and c.noteId=" + noteId);

		int status = query.executeUpdate();
		session.close();
		return status;
	}

}

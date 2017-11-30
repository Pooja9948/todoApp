package com.bridgelabz.Note.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Note.DAO.NoteDAO;
import com.bridgelabz.Note.model.NoteCollaborate;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.User.model.UserDetails;

public class NoteServiceImpl implements NoteService {
	@Autowired
	NoteDAO noteDao;

	public void createNote(NoteDetails noteDetails) {
		noteDao.createNote(noteDetails);
	}

	public void updateNote(NoteDetails noteDetails) {
		noteDao.updateNote(noteDetails);
	}

	public void deleteNote(int noteId) {
		noteDao.deleteNote(noteId);
	}

	public List<NoteDetails> getAllNotes(UserDetails userDetails) {
		return noteDao.getAllNotes(userDetails);
	}

	public NoteDetails getNoteById(int noteId) {
		return noteDao.getNoteById(noteId);
	}

	@Override
	public void deleteScheduleNote() {
		noteDao.deleteScheduleNote();
	}

	public void deleteAllNote() {
		noteDao.deleteAllNote();
	}

	public int saveCollborator(NoteCollaborate collborate) {

		return noteDao.saveCollborator(collborate);
	}

	public List<UserDetails> getListOfUser(int noteId) {

		return noteDao.getListOfUser(noteId);
	}

	public Set<NoteDetails> getCollboratedNotes(int userId) {

		return noteDao.getCollboratedNotes(userId);
	}

	public int removeCollborator(int shareWith, int noteId) {

		return noteDao.removeCollborator(shareWith, noteId);
	}
}

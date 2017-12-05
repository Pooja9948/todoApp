package com.bridgelabz.Note.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Note.DAO.NoteDAO;
import com.bridgelabz.Note.model.NoteCollaborate;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.Note.model.NoteLabel;
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

	public List<NoteDetails> getCollboratedNotes(int userId) {

		return noteDao.getCollboratedNotes(userId);
	}

	public int removeCollborator(int shareWith, int noteId) {

		return noteDao.removeCollborator(shareWith, noteId);
	}

	@Override
	public void saveLabel(NoteLabel labels) {
		noteDao.saveLabel(labels);
	}

	@Override
	public boolean deleteLabelById(int id) {
		noteDao.deleteById(id);
		return true;
	}

	@Override
	public List<NoteLabel> getLabels(UserDetails user) {
		return noteDao.getLabels(user);
	}

	@Override
	public NoteLabel getLabelById(int labelId) {
		return noteDao.getLabelById(labelId);
	}

	@Override
	public boolean editLabel(NoteLabel label) {
		noteDao.editLabel(label);
		return true;
	}

	@Override
	public NoteLabel getLabelByName(String labelName) {
		return noteDao.getLabelByName(labelName);
	}
	
	public boolean removeNoteId(int id){
		return noteDao.removeNoteId(id);
	}
}

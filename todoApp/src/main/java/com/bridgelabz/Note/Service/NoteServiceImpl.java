package com.bridgelabz.Note.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Note.DAO.NoteDAO;
import com.bridgelabz.Note.model.NoteCollaborate;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.Note.model.NoteLabel;
import com.bridgelabz.User.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public class NoteServiceImpl implements NoteService {
	@Autowired
	NoteDAO noteDao;

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#createNote(com.bridgelabz.Note.model.NoteDetails)
	 * create notes and return the noteDetails object
	 */
	public NoteDetails createNote(NoteDetails noteDetails) {
		return noteDao.createNote(noteDetails);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#updateNote(com.bridgelabz.Note.model.NoteDetails)
	 * update notes by noteDetails
	 */
	public void updateNote(NoteDetails noteDetails) {
		noteDao.updateNote(noteDetails);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#deleteNote(int)
	 * delete notes by noteId
	 */
	public void deleteNote(int noteId) {
		noteDao.deleteNote(noteId);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#getAllNotes(com.bridgelabz.User.model.UserDetails)
	 * get n=all notes by userDetails
	 */
	public List<NoteDetails> getAllNotes(UserDetails userDetails) {
		return noteDao.getAllNotes(userDetails);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#getNoteById(int)
	 * get note by noteId
	 */
	public NoteDetails getNoteById(int noteId) {
		return noteDao.getNoteById(noteId);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#deleteScheduleNote()
	 * delete schedule note
	 */
	@Override
	public void deleteScheduleNote() {
		noteDao.deleteScheduleNote();
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#deleteAllNote()
	 * delete all notes
	 */
	public void deleteAllNote() {
		noteDao.deleteAllNote();
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#saveCollborator(com.bridgelabz.Note.model.NoteCollaborate)
	 * save collaborator
	 */
	public int saveCollborator(NoteCollaborate collborate) {

		return noteDao.saveCollborator(collborate);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#getListOfUser(int)
	 * get list of users by noteId
	 */
	public List<UserDetails> getListOfUser(int noteId) {

		return noteDao.getListOfUser(noteId);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#getCollboratedNotes(int)
	 * get collaborated notes by userId
	 */
	public List<NoteDetails> getCollboratedNotes(int userId) {

		return noteDao.getCollboratedNotes(userId);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#removeCollborator(int, int)
	 * remove collaborator 
	 */
	public int removeCollborator(int shareWith, int noteId) {

		return noteDao.removeCollborator(shareWith, noteId);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#saveLabel(com.bridgelabz.Note.model.NoteLabel)
	 * save label by using labels object
	 */
	@Override
	public void saveLabel(NoteLabel labels) {
		noteDao.saveLabel(labels);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#deleteLabelById(int)
	 * delete label by id
	 */
	@Override
	public boolean deleteLabelById(int id) {
		noteDao.deleteById(id);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#getLabels(com.bridgelabz.User.model.UserDetails)
	 * get labels by user object
	 */
	@Override
	public List<NoteLabel> getLabels(UserDetails user) {
		return noteDao.getLabels(user);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#getLabelById(int)
	 * get label by labelId
	 */
	@Override
	public NoteLabel getLabelById(int labelId) {
		return noteDao.getLabelById(labelId);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#editLabel(com.bridgelabz.Note.model.NoteLabel)
	 * edit label by label object
	 */
	@Override
	public boolean editLabel(NoteLabel label) {
		noteDao.editLabel(label);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#getLabelByName(java.lang.String)
	 * get label by name using labelName
	 */
	@Override
	public NoteLabel getLabelByName(String labelName) {
		return noteDao.getLabelByName(labelName);
	}
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.Service.NoteService#removeNoteId(int)
	 * remove note by id
	 */
	public boolean removeNoteId(int id){
		return noteDao.removeNoteId(id);
	}
}

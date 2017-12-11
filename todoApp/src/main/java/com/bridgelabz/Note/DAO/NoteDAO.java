package com.bridgelabz.Note.DAO;

import java.util.List;
import java.util.Set;

import com.bridgelabz.Note.model.NoteCollaborate;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.Note.model.NoteLabel;
import com.bridgelabz.Note.model.NoteUrl;
import com.bridgelabz.User.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public interface NoteDAO {
	
	/**
	 * @param note
	 * @return passing the parameter to create the note
	 */
	public NoteDetails createNote(NoteDetails note);

	/**
	 * @param noteDetails
	 * passing the parameter to update the note
	 */
	public void updateNote(NoteDetails noteDetails);

	/**
	 * @param noteId
	 * passing the id to delete the note
	 */
	public void deleteNote(int noteId);

	/**
	 * @param noteId
	 * @return
	 * get the note by id
	 */
	public NoteDetails getNoteById(int noteId);

	/**
	 * @param userDetails
	 * @return
	 * get all notes of the users
	 */
	public List<NoteDetails> getAllNotes(UserDetails userDetails);

	/**
	 * delete scheduler notes 
	 */
	public void deleteScheduleNote();

	/**
	 * delete all notes
	 */
	public void deleteAllNote();

	/**
	 * @param collborate
	 * @return
	 * save collaborator
	 */
	public int saveCollborator(NoteCollaborate collborate);

	/**
	 * @param noteId
	 * @return
	 * get list of users by noteId
	 */
	public List<UserDetails> getListOfUser(int noteId);

	/**
	 * @param userId
	 * @return
	 * get collaborate notes by userid
	 */
	public List<NoteDetails> getCollboratedNotes(int userId);

	/**
	 * @param shareWith
	 * @param noteId
	 * @return
	 * remove sharewith by using noteId
	 */
	public int removeCollborator(int shareWith, int noteId);

	/**
	 * @param labels
	 * save labels
	 */
	void saveLabel(NoteLabel labels);

	/**
	 * @param id
	 */
	void deleteById(int id);

	/**
	 * @param user
	 * @return
	 * get all labels
	 */
	List<NoteLabel> getLabels(UserDetails user);

	/**
	 * @param labelId
	 * @return
	 * get label by labelId
	 */
	NoteLabel getLabelById(int labelId);

	/**
	 * @param label
	 * @return
	 * edit label
	 */
	boolean editLabel(NoteLabel label);

	/**
	 * @param labelName
	 * @return
	 * get label by labelname
	 */
	NoteLabel getLabelByName(String labelName);

	/**
	 * @param id
	 * @return
	 * remove note by id
	 */
	public boolean removeNoteId(int id);

	// public void saveNoteUrls(NoteUrl noteUrls,NoteDetails noteDetails);

	/**
	 * @param noteUrls
	 * @param noteDetails
	 * save note Urls
	 */
	void saveNoteUrls(Set<NoteUrl> noteUrls, NoteDetails noteDetails);
}

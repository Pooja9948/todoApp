package com.bridgelabz.Note.Service;

import java.util.List;
import java.util.Set;

import com.bridgelabz.Note.model.NoteCollaborate;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.Note.model.NoteLabel;
import com.bridgelabz.User.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public interface NoteService {
	
	/**
	 * @param noteDetails
	 * @return
	 * creating notes
	 */
	public NoteDetails createNote(NoteDetails noteDetails);

	/**
	 * @param noteDetails
	 * update notes by using noteDetails
	 */
	public void updateNote(NoteDetails noteDetails);

	/**
	 * @param noteId
	 * deleteNote by noteId
	 */
	public void deleteNote(int noteId);

	/**
	 * @param noteId
	 * @return
	 * get note by noteId
	 */
	public NoteDetails getNoteById(int noteId);

	/**
	 * @param userDetails
	 * @return
	 * get all notes by userDetails
	 */
	public List<NoteDetails> getAllNotes(UserDetails userDetails);

	/**
	 * delete schedule notes
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
	 * get collaborated notes by userId
	 */
	public List<NoteDetails> getCollboratedNotes(int userId);

	/**
	 * @param shareWith
	 * @param noteId
	 * @return
	 * remove sharewith by using parameters sharewith, noteId
	 */
	public int removeCollborator(int shareWith, int noteId);

	/**
	 * @param labels
	 * save labels
	 */
	void saveLabel(NoteLabel labels);

	/**
	 * @param id
	 * @return
	 * selete label by id
	 */
	boolean deleteLabelById(int id);

	/**
	 * @param user
	 * @return
	 * get label by user
	 */
	List<NoteLabel> getLabels(UserDetails user);

	/**
	 * @param labelId
	 * @return
	 * get label by labelId
	 */
	NoteLabel getLabelById(int labelId);

	/**
	 * @param labelName
	 * @return
	 * get label by labelName
	 */
	NoteLabel getLabelByName(String labelName);

	/**
	 * @param label
	 * @return
	 * edit label
	 */
	boolean editLabel(NoteLabel label);
	
	boolean removeNoteId(int id);
}

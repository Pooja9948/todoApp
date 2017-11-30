package com.bridgelabz.Note.DAO;

import java.util.List;
import java.util.Set;

import com.bridgelabz.Note.model.NoteCollaborate;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.User.model.UserDetails;

public interface NoteDAO {
	public void createNote(NoteDetails note);

	public void updateNote(NoteDetails noteDetails);

	public void deleteNote(int noteId);

	public NoteDetails getNoteById(int noteId);

	public List<NoteDetails> getAllNotes(UserDetails userDetails);
	
	public void deleteScheduleNote();
	
	public void deleteAllNote();
	
	public int saveCollborator(NoteCollaborate collborate);

	public List<UserDetails> getListOfUser(int noteId);

	public Set<NoteDetails> getCollboratedNotes(int userId);

	public int removeCollborator(int shareWith, int noteId);
}

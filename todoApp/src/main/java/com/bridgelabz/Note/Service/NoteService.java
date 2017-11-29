package com.bridgelabz.Note.Service;

import java.util.List;

import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.User.model.UserDetails;

public interface NoteService {
	public void createNote(NoteDetails noteDetails);

	public void updateNote(NoteDetails noteDetails);

	public void deleteNote(int noteId);

	public NoteDetails getNoteById(int noteId);

	public List<NoteDetails> getAllNotes(UserDetails userDetails);

	public void deleteScheduleNote();
	
	public void deleteAllNote();
}

package com.bridgelabz.Note.DAO;

import java.util.List;

import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.User.model.UserDetails;

public interface NoteDAO {
	public void createNote(NoteDetails note);

	public void updateNote(NoteDetails noteDetails);

	public void deleteNote(int noteId);

	public NoteDetails getNoteById(int noteId);

	public List<NoteDetails> getAllNotes(UserDetails userDetails);
}

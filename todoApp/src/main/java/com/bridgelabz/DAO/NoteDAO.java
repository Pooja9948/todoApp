package com.bridgelabz.DAO;

import java.util.List;

import com.bridgelabz.model.NoteDetails;
import com.bridgelabz.model.UserDetails;

public interface NoteDAO {
	public void createNote(NoteDetails note);
	public void updateNote(NoteDetails noteDetails);
	public void deleteNote(int noteId);
	public NoteDetails getNoteById(int noteId);
	public List<NoteDetails> getAllNotes(UserDetails userDetails);
}

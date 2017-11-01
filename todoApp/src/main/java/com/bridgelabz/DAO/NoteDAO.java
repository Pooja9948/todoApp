package com.bridgelabz.DAO;

import com.bridgelabz.model.NoteDetails;

public interface NoteDAO {
	public void createNote(NoteDetails note);
	public void updateNote(NoteDetails noteDetails);
	public void deleteNote(int noteId);
	public NoteDetails getNoteById(int noteId);
}

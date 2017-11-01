package com.bridgelabz.service;

import com.bridgelabz.model.NoteDetails;

public interface NoteService {
	public void createNote(NoteDetails noteDetails);
	public void updateNote(NoteDetails noteDetails);
	public void deleteNote(int noteId);
	public NoteDetails getNoteById(int noteId);
}

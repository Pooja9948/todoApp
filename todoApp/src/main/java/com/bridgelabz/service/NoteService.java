package com.bridgelabz.service;

import java.util.List;

import com.bridgelabz.model.NoteDetails;
import com.bridgelabz.model.UserDetails;

public interface NoteService {
	public void createNote(NoteDetails noteDetails);
	public void updateNote(NoteDetails noteDetails);
	public void deleteNote(int noteId);
	public NoteDetails getNoteById(int noteId);
	public List<NoteDetails> getAllNotes(UserDetails userDetails);
}

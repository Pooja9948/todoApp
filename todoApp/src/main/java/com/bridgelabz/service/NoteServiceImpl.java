package com.bridgelabz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.bridgelabz.DAO.NoteDAO;
import com.bridgelabz.model.NoteDetails;
import com.bridgelabz.model.UserDetails;

public class NoteServiceImpl implements NoteService {
	@Autowired
	NoteDAO noteDao;
	
	public void createNote(NoteDetails noteDetails){
		noteDao.createNote(noteDetails);
	}
	
	public void updateNote(NoteDetails noteDetails){
		noteDao.updateNote(noteDetails);
	}
	
	public void deleteNote(int noteId){
		noteDao.deleteNote(noteId);
	}
	
	public List<NoteDetails> getAllNotes(UserDetails userDetails){
		return noteDao.getAllNotes(userDetails);
	}
	
	public NoteDetails getNoteById(int noteId)
	{
		return noteDao.getNoteById(noteId);
	}
}

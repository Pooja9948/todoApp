package com.bridgelabz.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.NoteDetails;
import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.NoteService;

@RestController
public class NoteController {
	
	@Autowired
	NoteService noteService;
	
	@RequestMapping(value="/createNote", method= RequestMethod.POST)
	public void createNote(@RequestBody NoteDetails noteDetails,HttpSession session){
		UserDetails user = (UserDetails) session.getAttribute("user");
		noteDetails.setUser(user);
		Date date = new Date();	
		noteDetails.setCreateddate(date);
		noteDetails.setModifiedDate(date);
		noteService.createNote(noteDetails);
		System.out.println("Note created!!");
	}
	
	@RequestMapping(value="/updateNote", method= RequestMethod.PUT)
	public void updateNote(@RequestBody NoteDetails noteDetails){
		
		int id = noteDetails.getId();
		
		NoteDetails  note = noteService.getNoteById(id);
		
		Date current = note.getCreateddate();
		noteDetails.setCreateddate(current);
		
		UserDetails user= note.getUser();
		noteDetails.setUser(user);
		
		
		System.out.println("note sid "+id +" current :"+noteDetails.getCreateddate()+" user : "+noteDetails.getUser());
		noteService.updateNote(noteDetails);
		System.out.println("note is updated");
		
		
	}
	
	@RequestMapping(value="/deleteNote/{id}", method= RequestMethod.DELETE)
	public void deleteNote(@PathVariable("id") int noteId){
		System.out.println("id : "+noteId);
		noteService.deleteNote(noteId);
		System.out.println("note is deleted");
	}
	@RequestMapping(value="/getAllNotes", method= RequestMethod.GET)
	public List<NoteDetails> getAllNotes(HttpSession session,HttpServletRequest request){
		UserDetails userDetails = (UserDetails) request.getAttribute("user");
		
		List<NoteDetails>  notes = noteService.getAllNotes(userDetails);
		System.out.println("all notes : "+notes);
		return notes;
	}
}

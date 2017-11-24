package com.bridgelabz.Note.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Note.Service.NoteService;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.User.Service.UserService;
import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.response.CustomResponse;
import com.bridgelabz.Util.response.Response;

/*import com.bridgelabz.Util.CustomResponse;
import com.bridgelabz.Util.Response;
import com.bridgelabz.model.NoteDetails;
import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.NoteService;*/

@RestController
@RequestMapping(value = "/user")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ResponseEntity<Response> createNote(@RequestBody NoteDetails noteDetails, HttpSession session,HttpServletRequest request) {
		//UserDetails user = (UserDetails) session.getAttribute("user");
		int userId = (int) request.getAttribute("userId");
		
		UserDetails user = userService.getUserById(userId);
		
		noteDetails.setUser(user);
		if (user != null) {
			Date date = new Date();
			noteDetails.setCreateddate(date);
			noteDetails.setModifiedDate(date);
			noteService.createNote(noteDetails);
			System.out.println("Note created!!");
			CustomResponse  customResponse = new CustomResponse();
			customResponse.setMessage("Note create successfully");
			return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
		}
		Response response = new Response();
		response.setMessage("Please login first");
		return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/updateNote", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateNote(@RequestBody NoteDetails noteDetails) {

		System.out.println("Note status: " + noteDetails.isPin());
		int id = noteDetails.getId();

		NoteDetails note = noteService.getNoteById(id);

		Date current = note.getCreateddate();
		noteDetails.setCreateddate(current);

		UserDetails user = note.getUser();
		noteDetails.setUser(user);

		System.out.println(
				"note sid " + id + " current :" + noteDetails.getCreateddate() + " user : " + noteDetails.getUser()+"note pin "+noteDetails.isPin());
		noteService.updateNote(noteDetails);
		System.out.println("note is updated");
		
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage("Note updated successfully...");
		return ResponseEntity.ok(customResponse);

	}

	@RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable("id") int noteId) {
		NoteDetails note = new NoteDetails();
		note.setId(noteId);
		System.out.println("id : " + noteId);
		noteService.deleteNote(noteId);
		System.out.println("note is deleted");
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage("Note deleted successfully!!!");
		return ResponseEntity.ok(customResponse);
	}

	@RequestMapping(value = "/getAllNotes", method = RequestMethod.GET)
	public List<NoteDetails> getAllNotes(HttpServletRequest request) {
		
		int id = (int) request.getAttribute("userId");
		
		UserDetails user = userService.getUserById(id);
		System.out.println("id---------------------------->"+id);
	
		List<NoteDetails> notes = noteService.getAllNotes(user);
		//System.out.println("all notes : " + notes);
		return notes;
	}
}

package com.bridgelabz.Controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
		Date date = new Date();
		//noteDetails.setCreatedDate(date);
		//noteDetails.setModifiedDate(date);
		//noteService.createNote(noteDetails);
		//noteService.createNote(noteDetails);
	}
}

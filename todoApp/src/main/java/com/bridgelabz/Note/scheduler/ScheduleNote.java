package com.bridgelabz.Note.scheduler;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Note.Service.NoteService;



/**
 * @author Pooja todoApp
 *
 */
public class ScheduleNote {
	@Autowired
	NoteService noteService;
	
	/**
	 * running the scheduler to delete the notes which notes have isTrash=1
	 */
	public void noteSchedule() {
		System.out.println("before spring scheduler");
		noteService.deleteScheduleNote();
        System.out.println("I am called by Spring scheduler");
    }
	
}

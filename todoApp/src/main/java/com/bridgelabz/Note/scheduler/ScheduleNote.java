package com.bridgelabz.Note.scheduler;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Note.Service.NoteService;



public class ScheduleNote {
	@Autowired
	NoteService noteService;
	
	public void noteSchedule() {
		System.out.println("before spring scheduler");
		noteService.deleteScheduleNote();
        System.out.println("I am called by Spring scheduler");
    }
	
}

package com.bridgelabz.Util.response;

import java.util.List;

import com.bridgelabz.Note.model.NoteDetails;

public class CustomResponse extends Response {
	List<NoteDetails> notes;

	public List<NoteDetails> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteDetails> notes) {
		this.notes = notes;
	}
}

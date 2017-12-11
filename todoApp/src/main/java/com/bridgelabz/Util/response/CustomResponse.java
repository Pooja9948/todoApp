package com.bridgelabz.Util.response;

import java.util.List;

import com.bridgelabz.Note.model.NoteDetails;

/**
 * @author Pooja todoApp
 *
 */
public class CustomResponse extends Response {
	List<NoteDetails> notes;

	/**
	 * @return notes
	 */
	public List<NoteDetails> getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(List<NoteDetails> notes) {
		this.notes = notes;
	}
}

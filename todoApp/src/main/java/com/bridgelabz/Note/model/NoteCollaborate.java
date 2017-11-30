package com.bridgelabz.Note.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.User.model.UserDetails;

@Entity
@Table
public class NoteCollaborate {
	
	@Id
	@GenericGenerator(name = "col", strategy = "increment")
	@GeneratedValue(generator = "col")
	private int collaboratorId;
	
	@ManyToOne
	@JoinColumn
	private UserDetails ownerId;
	
	@ManyToOne
	@JoinColumn
	private UserDetails shareId;
	
	@ManyToOne
	@JoinColumn
	private NoteDetails noteId;

	public int getCollaboratorId() {
		return collaboratorId;
	}

	public UserDetails getOwnerId() {
		return ownerId;
	}

	public UserDetails getShareId() {
		return shareId;
	}

	public NoteDetails getNoteId() {
		return noteId;
	}

	public void setCollaboratorId(int collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	public void setOwnerId(UserDetails ownerId) {
		this.ownerId = ownerId;
	}

	public void setShareId(UserDetails shareId) {
		this.shareId = shareId;
	}

	public void setNoteId(NoteDetails noteId) {
		this.noteId = noteId;
	}

	@Override
	public String toString() {
		return "Collaborater [collaboratorId=" + collaboratorId + ", ownerId=" + ownerId + ", shareId=" + shareId
				+ ", noteId=" + noteId + "]";
}
}

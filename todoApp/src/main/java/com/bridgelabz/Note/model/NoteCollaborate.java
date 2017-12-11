package com.bridgelabz.Note.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.User.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
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

	/**
	 * @return collaboratorId
	 */
	public int getCollaboratorId() {
		return collaboratorId;
	}

	/**
	 * @return ownerId
	 */
	public UserDetails getOwnerId() {
		return ownerId;
	}

	/**
	 * @return shareId
	 */
	public UserDetails getShareId() {
		return shareId;
	}

	/**
	 * @return noteId
	 */
	public NoteDetails getNoteId() {
		return noteId;
	}

	/**
	 * @param collaboratorId
	 */
	public void setCollaboratorId(int collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	/**
	 * @param ownerId
	 */
	public void setOwnerId(UserDetails ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @param shareId
	 */
	public void setShareId(UserDetails shareId) {
		this.shareId = shareId;
	}

	/**
	 * @param noteId
	 */
	public void setNoteId(NoteDetails noteId) {
		this.noteId = noteId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Collaborater [collaboratorId=" + collaboratorId + ", ownerId=" + ownerId + ", shareId=" + shareId
				+ ", noteId=" + noteId + "]";
}
}

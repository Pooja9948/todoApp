package com.bridgelabz.Note.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bridgelabz.User.model.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "note")
public class NoteDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_detailid")
	int id;
	String title;
	String description;
	Date createddate;
	Date modifiedDate;
	
	private boolean isArchived;
	private boolean isPin;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_detailid")
	private UserDetails userDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public UserDetails getUser() {
		return userDetails;
	}

	public void setUser(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	public boolean getArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}
	@Override
	public String toString() {
		return "NoteDetails [id=" + id + ", title=" + title + ", description=" + description + ", createddate="
				+ createddate + ", modifiedDate=" + modifiedDate + ", userDetails=" + userDetails + "]";
	}

}

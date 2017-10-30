package com.bridgelabz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class NoteDetails {
	int id;
	String title;
	String description;
	Date date;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_detailid")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_detailid")
	private UserDetails userDetails;
	
	public UserDetails getUser() {
		return userDetails;
	}

	public void setUser(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	@Override
	public String toString() {
		return "NoteDetails [id=" + id + ", title=" + title + ", description=" + description + ", date=" + date
				+ ", userDetails=" + userDetails + "]";
	}

	
}

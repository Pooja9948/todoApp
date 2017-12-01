package com.bridgelabz.Note.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.User.model.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NoteLabel { 
	@Id  
	 @GeneratedValue(strategy=GenerationType.AUTO, generator="mygen")
	 @GenericGenerator(name="mygen",strategy="native")
	 @Column(name = "label_id")  
	 private int labelId;
	
	@Column(name="label_name")
	private String labelName;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private UserDetails user;
	
	@ManyToMany(mappedBy="alLabels")
	@JsonIgnore
	private Set<NoteDetails> alNote = new HashSet<>();
	
	public int getLabelId() {
		return labelId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	@JsonIgnore
	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}
	@JsonIgnore
	public Set<NoteDetails> getAlNote() {
		return alNote;
	}

	public void setAlNote(Set<NoteDetails> alNote) {
		this.alNote = alNote;
	}
	

	public void setCreatedTime(Date date) {
		// TODO Auto-generated method stub
		
	}

}

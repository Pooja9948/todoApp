package com.bridgelabz.Note.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.User.model.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pooja todoApp
 *
 */
@Entity
@Table
public class NoteLabel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mygen")
	@GenericGenerator(name = "mygen", strategy = "native")
	@Column(name = "label_id")
	private int labelId;

	@Column(name = "label_name")
	private String labelName;

	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private UserDetails user;

	@ManyToMany(mappedBy = "alLabels")
	@JsonIgnore
	private Set<NoteDetails> alNote = new HashSet<>();

	/**
	 * @return labelId
	 */
	public int getLabelId() {
		return labelId;
	}

	/**
	 * @param labelId
	 */
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	/**
	 * @return labelName
	 */
	public String getLabelName() {
		return labelName;
	}

	/**
	 * @param labelName
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	/**
	 * @return user
	 */
	@JsonIgnore
	public UserDetails getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(UserDetails user) {
		this.user = user;
	}

	/**
	 * @return alNote
	 */
	@JsonIgnore
	public Set<NoteDetails> getAlNote() {
		return alNote;
	}

	/**
	 * @param alNote
	 */
	public void setAlNote(Set<NoteDetails> alNote) {
		this.alNote = alNote;
	}

	public void setCreatedTime(Date date) {
		// TODO Auto-generated method stub

	}

}

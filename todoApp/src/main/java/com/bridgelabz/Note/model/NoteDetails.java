package com.bridgelabz.Note.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	String color;

	Date createddate;
	Date modifiedDate;

	private boolean isArchived;
	private boolean isPin;
	private boolean isTrash;
	private String reminder;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private String noteImage;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_detailid")
	private UserDetails userDetails;
	
	@ManyToMany
	@JoinTable(name = "note_label", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "label_id") })
	@JsonIgnore
	private Set<NoteLabel> alLabels = new HashSet<>();
	
	
	//@JsonIgnore
	@OneToMany(mappedBy = "note",cascade=CascadeType.ALL)
	private Set<NoteUrl> noteUrls = new HashSet<>();
	
	public Set<NoteUrl> getNoteUrls() {
		return noteUrls;
	}

	public void setNoteUrls(Set<NoteUrl> noteUrls) {
		this.noteUrls = noteUrls;
	}
	
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getNoteImage() {
		return noteImage;
	}

	public void setNoteImage(String noteImage) {
		this.noteImage = noteImage;
	}

	/*@ManyToMany
	@JoinTable(name = "note_user", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "userId") })
	@JsonIgnore
	List<UserDetails> alUser = new ArrayList<>();*/
	
	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/*public List<UserDetails> getAlUser() {
		return alUser;
	}

	public void setAlUser(List<UserDetails> alUser) {
		this.alUser = alUser;
	}*/


	public Set<NoteLabel> getAlLabels() {
		return alLabels;
	}

	public void setAlLabels(Set<NoteLabel> alLabels) {
		this.alLabels = alLabels;
	}

	public Set<NoteLabel> getLabels() {
		return alLabels;
	}

	public void setLabels(Set<NoteLabel> alLabels) {
		this.alLabels = alLabels;
	}
	
}

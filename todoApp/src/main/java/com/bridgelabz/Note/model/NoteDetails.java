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

/**
 * @author Pooja todoApp
 *
 */
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
	
	/**
	 * @return noteUrls
	 */
	public Set<NoteUrl> getNoteUrls() {
		return noteUrls;
	}

	/**
	 * @param noteUrls
	 */
	public void setNoteUrls(Set<NoteUrl> noteUrls) {
		this.noteUrls = noteUrls;
	}
	
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return createddate
	 */
	public Date getCreateddate() {
		return createddate;
	}

	/**
	 * @param createddate
	 */
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	/**
	 * @return modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return userDetails
	 */
	public UserDetails getUser() {
		return userDetails;
	}

	/**
	 * @param userDetails
	 */
	public void setUser(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * @return isArchived
	 */
	public boolean getArchived() {
		return isArchived;
	}

	/**
	 * @param isArchived
	 */
	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	/**
	 * @return isTrash
	 */
	public boolean isTrash() {
		return isTrash;
	}

	/**
	 * @param isTrash
	 */
	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	/**
	 * @return isPin
	 */
	public boolean isPin() {
		return isPin;
	}

	/**
	 * @param isPin
	 */
	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	/**
	 * @return reminder
	 */
	public String getReminder() {
		return reminder;
	}

	/**
	 * @param reminder
	 */
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	/**
	 * @returnnoteImage
	 */
	public String getNoteImage() {
		return noteImage;
	}

	/**
	 * @param noteImage
	 */
	public void setNoteImage(String noteImage) {
		this.noteImage = noteImage;
	}

	/*@ManyToMany
	@JoinTable(name = "note_user", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "userId") })
	@JsonIgnore
	List<UserDetails> alUser = new ArrayList<>();*/
	
	/**
	 * @returnuserDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * @param userDetails
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/*public List<UserDetails> getAlUser() {
		return alUser;
	}

	public void setAlUser(List<UserDetails> alUser) {
		this.alUser = alUser;
	}*/


	/**
	 * @return alLabels
	 */
	public Set<NoteLabel> getAlLabels() {
		return alLabels;
	}

	/**
	 * @param alLabels
	 */
	public void setAlLabels(Set<NoteLabel> alLabels) {
		this.alLabels = alLabels;
	}

	/**
	 * @return alLabels
	 */
	public Set<NoteLabel> getLabels() {
		return alLabels;
	}

	/**
	 * @param alLabels
	 */
	public void setLabels(Set<NoteLabel> alLabels) {
		this.alLabels = alLabels;
	}
	
}

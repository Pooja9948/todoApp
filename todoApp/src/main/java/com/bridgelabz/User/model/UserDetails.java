package com.bridgelabz.User.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.Note.model.NoteLabel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pooja todoApp
 *
 */
@Entity
@Table(name = "user_detail")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_detailid")
	private int userId;

	private String firstname;

	private String lastname;

	@Column(unique = true)
	private String email;

	private String password;

	private String confirmpassword;

	private String mobileno;

	private boolean isActivated;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private String profileImage; 
	
	/**
	 * @return profileImage
	 */
	public String getProfileImage() {
		return profileImage;
	}

	/**
	 * @param profileImage
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return isActivated
	 */
	public boolean isActivated() {
		return isActivated;
	}

	/**
	 * @param isActivated
	 */
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "userDetails")
	private Set<NoteDetails> notes = new HashSet<>();

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<NoteLabel> alLabels;

	/*@ManyToMany(mappedBy = "alUser")
	@JsonIgnore
	private List<NoteDetails> alNotes = new ArrayList<>();*/
	
	/**
	 * @return id
	 */
	public int getId() {
		return userId;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.userId = id;
	}

	/**
	 * @return firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return notes
	 */
	public Set<NoteDetails> getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(Set<NoteDetails> notes) {
		this.notes = notes;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return confirmpassword
	 */
	public String getConfirmpassword() {
		return confirmpassword;
	}

	/**
	 * @param confirmpassword
	 */
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	/**
	 * @return mobileno
	 */
	public String getMobileno() {
		return mobileno;
	}

	/**
	 * @param mobileno
	 */
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	
	

}

package com.bridgelabz.Note.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pooja todoApp
 *
 */
@Entity
@Table(name = "NOTE_URL")
public class NoteUrl {
	
	@Id
	@GenericGenerator(name = "col", strategy = "increment")
	@GeneratedValue(generator = "col")
	private int urlId;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private String urlImage;
	private String domainName;
	private String urlTitle;
	private String url;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "note_id")
	private NoteDetails note;
	
	/**
	 * @return note
	 */
	public NoteDetails getNote() {
		return note;
	}
	/**
	 * @param note
	 */
	public void setNote(NoteDetails note) {
		this.note = note;
	}
	
	/**
	 * @return urlId
	 */
	public int getUrlId() {
		return urlId;
	}
	/**
	 * @param urlId
	 */
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	/**
	 * @return urlImage
	 */
	public String getUrlImage() {
		return urlImage;
	}
	/**
	 * @param urlImage
	 */
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	/**
	 * @return domainName
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * @param domainName
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	/**
	 * @return urlTitle
	 */
	public String getUrlTitle() {
		return urlTitle;
	}
	/**
	 * @param urlTitle
	 */
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}
	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NoteUrl [urlId=" + urlId + ", urlImage=" + urlImage + ", domainName=" + domainName + ", urlTitle="
				+ urlTitle + ", url=" + url + ", note=" + note + "]";
	}
	
}

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
	
	public NoteDetails getNote() {
		return note;
	}
	public void setNote(NoteDetails note) {
		this.note = note;
	}
	
	public int getUrlId() {
		return urlId;
	}
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getUrlTitle() {
		return urlTitle;
	}
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "NoteUrl [urlId=" + urlId + ", urlImage=" + urlImage + ", domainName=" + domainName + ", urlTitle="
				+ urlTitle + ", url=" + url + ", note=" + note + "]";
	}
	
}

package com.mj.fileupload.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {
	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "relased_year")
	private int relasedYear;

	public Movie() {
	}

	public Movie(long id, String title, String description, int relasedYear) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.relasedYear = relasedYear;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public int getRelasedYear() {
		return relasedYear;
	}

	public void setRelasedYear(int relasedYear) {
		this.relasedYear = relasedYear;
	}

}
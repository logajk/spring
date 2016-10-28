package com.vector.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the WKST_STATUSREASON database table.
 * 
 */
@Entity
@Table(name="WKST_STATUSREASON")
@NamedQuery(name="WkstStatusreason.findAll", query="SELECT w FROM WkstStatusreason w")
public class WkstStatusreason implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private int id;

	private String description;

	@Column(name="TYPE")
	private String type;

	public WkstStatusreason() {
	}
	
	public WkstStatusreason(int id, String description, String type) {
		super();
		this.id = id;
		this.description = description;
		this.type = type;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WkstStatusreason [id=" + id + ", description=" + description
				+ ", type=" + type + "]";
	}
}
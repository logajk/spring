package com.vector.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the WKST_STATUS database table.
 * 
 */
@Entity
@Table(name="WKST_STATUS")
@NamedQuery(name="WkstStatus.findAll", query="SELECT w FROM WkstStatus w")
public class WkstStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;

	@Column(name="NAME")
	private String name;

	public WkstStatus() {
	}
	
	public WkstStatus(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WkstStatus [id=" + id + ", name=" + name + "]";
	}
}
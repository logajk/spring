package com.vector.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;




/**
 * The persistent class for the WKST_GROUP database table.
 * 
 */
@Entity
@Table(name="WKST_GROUP")
@NamedQuery(name="WkstGroup.findAll", query="SELECT w FROM WkstGroup w")
public class WkstGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private int id;

	private String description;

	@Column(name="NAME")
	private String name;
	
	@ManyToMany(mappedBy="grupos")
	private List<WkstWorkstation> workstations;

	public WkstGroup() {
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
		return "WkstGroup [id=" + id + ", description=" + description
				+ ", name=" + name + "]";
	}

	/**
	 * @return the workstations
	 */
	@JsonIgnore
	public List<WkstWorkstation> getWorkstations() {
		return workstations;
	}

	/**
	 * @param workstations the workstations to set
	 */
	public void setWorkstations(List<WkstWorkstation> workstations) {
		this.workstations = workstations;
	}
}
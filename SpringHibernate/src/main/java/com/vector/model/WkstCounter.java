package com.vector.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the WKST_COUNTER database table.
 * 
 */
@Entity
@Table(name="WKST_COUNTER")
@NamedQuery(name="WkstCounter.findAll", query="SELECT w FROM WkstCounter w")
@SequenceGenerator(name="COUNTER_SEQ", sequenceName="SEQ_WKST_COUNTER")
public class WkstCounter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COUNTER_SEQ")
	private long id;

	@Column(name="NAME")
	private String name;

	@Column(name="VALUE")
	private double value;

	@ManyToOne
	@JoinColumn(name="WKSTID")
	private WkstWorkstation workstation;
	
	public WkstCounter() {
	}
	
	public WkstCounter(long id, String name, double value, WkstWorkstation wkstid) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.workstation = wkstid;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public WkstWorkstation getWorkstation() {
		return this.workstation;
	}

	public void setWorkstation(WkstWorkstation wkstid) {
		this.workstation = wkstid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WkstCounter [id=" + id + ", name=" + name + ", value=" + value
				+ ", wkstid=" + workstation.getWkstid() + "]";
	}

}
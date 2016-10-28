package com.vector.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the WKST_COMMAND database table.
 * 
 */
@Entity
@Table(name="WKST_COMMAND")
@NamedQuery(name="WkstCommand.findAll", query="SELECT w FROM WkstCommand w")
@SequenceGenerator(name="COMMAND_SEQ", sequenceName="SEQ_WKST_COMMAND")
public class WkstCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMMAND_SEQ")
	@Column(name="ID")
	private int id;

	private String command;

	
	@ManyToOne
	@JoinColumn(name="WKSTID")
	private WkstWorkstation workstation;

	public WkstCommand() {
	}

	
	public WkstCommand(int id, String command, WkstWorkstation workstation) {
		super();
		this.id = id;
		this.command = command;
		this.workstation = workstation;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the workstation
	 */
	public WkstWorkstation getWorkstation() {
		return workstation;
	}


	/**
	 * @param workstation the workstation to set
	 */
	public void setWorkstation(WkstWorkstation workstation) {
		this.workstation = workstation;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WkstCommand [id=" + id + ", command=" + command + ", wkstid=" + workstation.getWkstid() + "]";
	}
}
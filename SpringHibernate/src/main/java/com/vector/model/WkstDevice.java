package com.vector.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the WKST_DEVICE database table.
 * 
 */
@Entity
@Table(name="WKST_DEVICE")
@NamedQuery(name="WkstDevice.findAll", query="SELECT w FROM WkstDevice w")
@SequenceGenerator(name="DEVICE_SEQ", sequenceName="SEQ_WKST_DEVICE")
public class WkstDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DEVICE_SEQ")
	private long id;

	private String logicalname;

	@Column(name="STATUS_ID")
	@Enumerated(EnumType.STRING)
	private Status statusId;

	@Column(name="TYPE_ID")
	@Enumerated(EnumType.STRING)
	private Type typeId;

	@ManyToOne
	@JoinColumn(name="WKSTID")
	private WkstWorkstation workStation;

	public WkstDevice() {
	}
	
	public WkstDevice(long id, String logicalname, Status statusId,
			Type typeId, WkstWorkstation workStation) {
		super();
		this.id = id;
		this.logicalname = logicalname;
		this.statusId = statusId;
		this.typeId = typeId;
		this.workStation = workStation;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogicalname() {
		return this.logicalname;
	}

	public void setLogicalname(String logicalname) {
		this.logicalname = logicalname;
	}

	public Status getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Status statusId) {
		this.statusId = statusId;
	}

	public Type getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Type typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the workStation
	 */
	public WkstWorkstation getWorkStation() {
		return workStation;
	}

	/**
	 * @param workStation the workStation to set
	 */
	public void setWorkStation(WkstWorkstation workStation) {
		this.workStation = workStation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WkstDevice [id=" + id + ", logicalname=" + logicalname
				+ ", statusId=" + statusId + ", typeId=" + typeId +  ", workStation_id=" + workStation.getWkstid() + "]";
	}
}
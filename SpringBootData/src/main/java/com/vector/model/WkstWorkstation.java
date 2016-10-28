package com.vector.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the WKST_WORKSTATION database table.
 * 
 */
@Entity
@Table(name="WKST_WORKSTATION")
@NamedQuery(name="WkstWorkstation.findAll", query="SELECT w FROM WkstWorkstation w")
public class WkstWorkstation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String wkstid;

	@Column(name="ADDRESS")
	private String address;

	private String branch;

	private String branchcode;

	private String city;

	private String country;

	private String county;

	private String description;

	@Temporal(TemporalType.DATE)
	private Date installationdate;

	private String ipaddress;

	private Integer ipport;

	private Timestamp lasttouched;

	private Timestamp lastupdate;

	private int numsecop;

	@Column(name="PREVSTATUS_ID")
	@Enumerated(EnumType.STRING)
	private Status prevstatusId;

	private String pushmode;

	private String rechargercode;

	private String remark;

	private BigDecimal securepercentage;

	private String serialnumber;

	private Timestamp settlementdate;

	private String socialdeprivation;

	@Column(name="STATE")
	private String state;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="STATUS_ID")
	private WkstStatus statusId;

	@Column(name="STATUSREASON_ID")
	private int statusreasonId;

	@Column(name="TIMESTAMP")
	private Long timestamp;

	@Column(name="TIMEZONE")
	private String timezone;

	@Temporal(TemporalType.DATE)
	private Date tmkdate;

	private int tpkcount;

	@Temporal(TemporalType.DATE)
	private Date tpkdate;

	private int trncount;

	private String vendorid;

	private String zipcode;
	
	
	@ManyToMany
	@JoinTable(
			name="WKST_WORKSTATION_HAS_GROUPS",
			joinColumns=@JoinColumn(name="WKSTID", referencedColumnName="WKSTID"),
			inverseJoinColumns=@JoinColumn(name="GROUP_ID", referencedColumnName="ID")
			)
	private List<WkstGroup> grupos;

	public WkstWorkstation() {
	}

	public String getWkstid() {
		return this.wkstid;
	}

	public void setWkstid(String wkstid) {
		this.wkstid = wkstid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranch() {
		return this.branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBranchcode() {
		return this.branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getInstallationdate() {
		return this.installationdate;
	}

	public void setInstallationdate(Date installationdate) {
		this.installationdate = installationdate;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Integer getIpport() {
		return this.ipport;
	}

	public void setIpport(Integer ipport) {
		this.ipport = ipport;
	}

	public Timestamp getLasttouched() {
		return this.lasttouched;
	}

	public void setLasttouched(Timestamp lasttouched) {
		this.lasttouched = lasttouched;
	}

	public Timestamp getLastupdate() {
		return this.lastupdate;
	}

	public void setLastupdate(Timestamp lastupdate) {
		this.lastupdate = lastupdate;
	}

	public int getNumsecop() {
		return this.numsecop;
	}

	public void setNumsecop(int numsecop) {
		this.numsecop = numsecop;
	}

	public Status getPrevstatusId() {
		return this.prevstatusId;
	}

	public void setPrevstatusId(Status prevstatusId) {
		this.prevstatusId = prevstatusId;
	}

	public String getPushmode() {
		return this.pushmode;
	}

	public void setPushmode(String pushmode) {
		this.pushmode = pushmode;
	}

	public String getRechargercode() {
		return this.rechargercode;
	}

	public void setRechargercode(String rechargercode) {
		this.rechargercode = rechargercode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSecurepercentage() {
		return this.securepercentage;
	}

	public void setSecurepercentage(BigDecimal securepercentage) {
		this.securepercentage = securepercentage;
	}

	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public Timestamp getSettlementdate() {
		return this.settlementdate;
	}

	public void setSettlementdate(Timestamp settlementdate) {
		this.settlementdate = settlementdate;
	}

	public String getSocialdeprivation() {
		return this.socialdeprivation;
	}

	public void setSocialdeprivation(String socialdeprivation) {
		this.socialdeprivation = socialdeprivation;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public WkstStatus getStatusId() {
		return this.statusId;
	}

	public void setStatusId(WkstStatus statusId) {
		this.statusId = statusId;
	}

	public int getStatusreasonId() {
		return this.statusreasonId;
	}

	public void setStatusreasonId(int statusreasonId) {
		this.statusreasonId = statusreasonId;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Date getTmkdate() {
		return this.tmkdate;
	}

	public void setTmkdate(Date tmkdate) {
		this.tmkdate = tmkdate;
	}

	public int getTpkcount() {
		return this.tpkcount;
	}

	public void setTpkcount(int tpkcount) {
		this.tpkcount = tpkcount;
	}

	public Date getTpkdate() {
		return this.tpkdate;
	}

	public void setTpkdate(Date tpkdate) {
		this.tpkdate = tpkdate;
	}

	public int getTrncount() {
		return this.trncount;
	}

	public void setTrncount(int trncount) {
		this.trncount = trncount;
	}

	public String getVendorid() {
		return this.vendorid;
	}

	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WkstWorkstation [wkstid=" + wkstid + ", address=" + address
				+ ", branch=" + branch + ", branchcode=" + branchcode
				+ ", city=" + city + ", country=" + country + ", county="
				+ county + ", description=" + description
				+ ", installationdate=" + installationdate + ", ipaddress="
				+ ipaddress + ", ipport=" + ipport + ", lasttouched="
				+ lasttouched + ", lastupdate=" + lastupdate + ", numsecop="
				+ numsecop + ", prevstatusId=" + prevstatusId + ", pushmode="
				+ pushmode + ", rechargercode=" + rechargercode + ", remark="
				+ remark + ", securepercentage=" + securepercentage
				+ ", serialnumber=" + serialnumber + ", settlementdate="
				+ settlementdate + ", socialdeprivation=" + socialdeprivation
				+ ", state=" + state + ", statusId=" + statusId
				+ ", statusreasonId=" + statusreasonId + ", timestamp="
				+ timestamp + ", timezone=" + timezone + ", tmkdate=" + tmkdate
				+ ", tpkcount=" + tpkcount + ", tpkdate=" + tpkdate
				+ ", trncount=" + trncount + ", vendorid=" + vendorid
				+ ", zipcode=" + zipcode + "]";
	}

	/**
	 * @return the grupos
	 */
	public List<WkstGroup> getGrupos() {
		return grupos;
	}

	/**
	 * @param grupos the grupos to set
	 */
	public void setGrupos(List<WkstGroup> grupos) {
		this.grupos = grupos;
	}
}
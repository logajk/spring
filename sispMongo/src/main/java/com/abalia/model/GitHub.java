package com.abalia.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="github")
public class GitHub {

	@Id
	private String id;
	
	private String id_demanda;
	
	private String contribution;
	
	private int repositories;
	
	private int followers;
	
	private Set<String> tecnologias;
	
	public GitHub(){
		tecnologias = new HashSet<>();
	}

	/**
	 * @return the tecnologias
	 */
	public Set<String> getTecnologias() {
		return tecnologias;
	}

	/**
	 * @param tecnologias the tecnologias to set
	 */
	public void setTecnologias(Set<String> tecnologias) {
		this.tecnologias = tecnologias;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id_demanda
	 */
	public String getId_demanda() {
		return id_demanda;
	}

	/**
	 * @param id_demanda the id_demanda to set
	 */
	public void setId_demanda(String id_demanda) {
		this.id_demanda = id_demanda;
	}

	/**
	 * @return the contribution
	 */
	public String getContribution() {
		return contribution;
	}

	/**
	 * @param contribution the contribution to set
	 */
	public void setContribution(String contribution) {
		this.contribution = contribution;
	}

	/**
	 * @return the repositories
	 */
	public int getRepositories() {
		return repositories;
	}

	/**
	 * @param repositories the repositories to set
	 */
	public void setRepositories(int repositories) {
		this.repositories = repositories;
	}

	/**
	 * @return the followers
	 */
	public int getFollowers() {
		return followers;
	}

	/**
	 * @param followers the followers to set
	 */
	public void setFollowers(int followers) {
		this.followers = followers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GitHub [id=" + id + ", id_demanda=" + id_demanda + ", contribution=" + contribution + ", repositories="
				+ repositories + ", followers=" + followers + ", tecnologias=" + tecnologias + "]";
	}
}

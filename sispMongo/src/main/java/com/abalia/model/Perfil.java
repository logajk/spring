package com.abalia.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Perfil {

	@Id
	private String id;
	
	private int repositorios;
	
	private int estrellas;
	
	private Set<String> conocimientos;
	
	private String id_demanda;
	
	private LocalDateTime fechaAlta;

	
	
	public Perfil(int repositorios, int estrellas, Set<String> conocimientos, String id_demanda) {
		super();
		this.repositorios = repositorios;
		this.estrellas = estrellas;
		this.conocimientos = conocimientos;
		this.id_demanda = id_demanda;
		this.fechaAlta = LocalDateTime.of(2017, 10, 19, 12, 00);
	}

	/**
	 * @return the fechaAlta
	 */
	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
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
	 * @return the repositorios
	 */
	public int getRepositorios() {
		return repositorios;
	}

	/**
	 * @param repositorios the repositorios to set
	 */
	public void setRepositorios(int repositorios) {
		this.repositorios = repositorios;
	}

	/**
	 * @return the estrellas
	 */
	public int getEstrellas() {
		return estrellas;
	}

	/**
	 * @param estrellas the estrellas to set
	 */
	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}

	/**
	 * @return the conocimientos
	 */
	public Set<String> getConocimientos() {
		return conocimientos;
	}

	/**
	 * @param conocimientos the conocimientos to set
	 */
	public void setConocimientos(Set<String> conocimientos) {
		this.conocimientos = conocimientos;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Perfil [id=" + id + ", repositorios=" + repositorios + ", estrellas=" + estrellas + ", conocimientos="
				+ conocimientos + ", id_demanda=" + id_demanda + ", fechaAlta=" + fechaAlta + "]";
	}
}

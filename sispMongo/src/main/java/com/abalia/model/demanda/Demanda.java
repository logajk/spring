package com.abalia.model.demanda;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.abalia.model.Portal;

@Document(collection="demandas")
public class Demanda {

	@Id
	private String id;
	
	private String nombre;
	
	private String apellidos;
	
	private Set<String> conocimientos;
	
	private String titulo;
	
	private Date fechaAlta;
	
	private Portal portal;
	
	private String email;
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the portal
	 */
	public Portal getPortal() {
		return portal;
	}

	/**
	 * @param portal the portal to set
	 */
	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	/**
	 * @return the fechaAlta
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Demanda(){
		conocimientos = new HashSet<>();
	}

	public Demanda(String nombre, Set<String> conocimientos) {
		super();
		this.nombre = nombre;
		this.conocimientos = conocimientos;
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Demanda [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", conocimientos="
				+ conocimientos + ", titulo=" + titulo + ", fechaAlta=" + fechaAlta + ", portal=" + portal + ", email="
				+ email + "]";
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
}

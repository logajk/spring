package com.abalia.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ofertas")
public class Oferta {

	
	@Id
	private String id;
	
	private String titulo;
	
	private Date fechaAlta;
	
	private Portal portal;
	
	private List<String> requisitos;
	
//	@DBRef(lazy=true)
//	private List<Requisito> requisito_id;
	
	/**
	 * @return the requisito_id
	 */
//	public List<Requisito> getRequisito_id() {
//		return requisito_id;
//	}

	/**
	 * @param requisito_id the requisito_id to set
	 */
//	public void setRequisito_id(List<Requisito> requisito_id) {
//		this.requisito_id = requisito_id;
//	}

	/**
	 * @return the requisitos
	 */
	public List<String> getRequisitos() {
		return requisitos;
	}

	/**
	 * @param requisitos the requisitos to set
	 */
	public void setRequisitos(List<String> requisitos) {
		this.requisitos = requisitos;
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

	public Oferta(){
		requisitos = new ArrayList<>();
	}
	
	/**
	 * Constructor de clase
	 * 
	 * @param id Identificador único
	 * @param titulo Tipo de oferta
	 * @param fecha Fecha de alta
	 */
	public Oferta(String id, String titulo, Date fechaAlta, Portal portal){
		this.id=id;
		this.titulo=titulo;
		this.fechaAlta=fechaAlta;
		this.portal=portal;
		if(requisitos==null)
			requisitos = new ArrayList<>();
//		if(requisito_id==null)
//			requisito_id = new ArrayList<>();
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Oferta [id=" + id + ", titulo=" + titulo + ", fechaAlta=" + fechaAlta + ", portal=" + portal
				+ ", requisitos=" + requisitos + "]";
	}
}

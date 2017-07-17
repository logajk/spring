package com.abalia.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonaDTO {

	private String nombre;
	
	private String apellidos;
	
	private Date fecha;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	
	public PersonaDTO(){
		
	}

	public PersonaDTO(String nombre, String apellidos, Date fecha) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fecha = fecha;
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

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PersonaDTO [nombre=" + nombre + ", apellidos=" + apellidos + ", fecha=" + sdf.format(fecha)+ "]";
	}
}

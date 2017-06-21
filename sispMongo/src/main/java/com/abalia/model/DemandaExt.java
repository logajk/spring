package com.abalia.model;

import java.math.BigDecimal;

public class DemandaExt extends Demanda {

	private BigDecimal puntuacion;

	public DemandaExt(Demanda demanda){
		super();
		this.setConocimientos(demanda.getConocimientos());
		this.setFechaAlta(demanda.getFechaAlta());
		this.setId(demanda.getId());
		this.setNombre(demanda.getNombre());
		this.setPortal(demanda.getPortal());
		this.setTitulo(demanda.getTitulo());
		
		this.puntuacion = new BigDecimal(0);
		this.puntuacion.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	
	public DemandaExt() {
		super();
		this.puntuacion = new BigDecimal(0);
		this.puntuacion.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public DemandaExt(BigDecimal puntuacion) {
		super();
		this.puntuacion.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.puntuacion = puntuacion;
	}

	/**
	 * @return the puntuacion
	 */
	public BigDecimal getPuntuacion() {
		return puntuacion;
	}

	/**
	 * @param puntuacion the puntuacion to set
	 */
	public void setPuntuacion(BigDecimal puntuacion) {
		this.puntuacion = puntuacion;
	}
}

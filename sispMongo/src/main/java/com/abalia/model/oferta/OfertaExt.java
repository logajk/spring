package com.abalia.model.oferta;

import java.math.BigDecimal;

public class OfertaExt extends Oferta {

	private BigDecimal puntuacion;
	
	public OfertaExt(Oferta oferta){
		super();
		this.setFechaAlta(oferta.getFechaAlta());
		this.setId(oferta.getId());
		this.setPortal(oferta.getPortal());
		this.setRequisitos(oferta.getRequisitos());
		this.setTitulo(oferta.getTitulo());
		
		this.puntuacion = new BigDecimal(0);
		this.puntuacion.setScale(2, BigDecimal.ROUND_HALF_UP);
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

package com.abalia.model.linkedin;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="linkedin")
public class Linkedin {

	@Id
	private String id;
	
	private String id_demanda;
	
	private Set<Profile> experiencia;
	
	private Set<Profile> educacion;
	
	private Set<String> aptitudes;

	public Linkedin(){
		this.experiencia = new HashSet<>();
		this.educacion = new HashSet<>();
		this.aptitudes = new HashSet<>();
	}
	
	public Linkedin(String id, String id_demanda){
		this.id = id;
		this.id_demanda = id_demanda;
		this.experiencia = new HashSet<>();
		this.educacion = new HashSet<>();
		this.aptitudes = new HashSet<>();
	}
	
	public Linkedin(String id, String id_demanda, Set<Profile> experiencia, Set<Profile> educacion,
			Set<String> aptitudes) {
		super();
		this.id = id;
		this.id_demanda = id_demanda;
		this.experiencia = experiencia;
		this.educacion = educacion;
		this.aptitudes = aptitudes;
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
	 * @return the experiencia
	 */
	public Set<Profile> getExperiencia() {
		return experiencia;
	}

	/**
	 * @param experiencia the experiencia to set
	 */
	public void setExperiencia(Set<Profile> experiencia) {
		this.experiencia = experiencia;
	}

	/**
	 * @return the educacion
	 */
	public Set<Profile> getEducacion() {
		return educacion;
	}

	/**
	 * @param educacion the educacion to set
	 */
	public void setEducacion(Set<Profile> educacion) {
		this.educacion = educacion;
	}

	/**
	 * @return the aptitudes
	 */
	public Set<String> getAptitudes() {
		return aptitudes;
	}

	/**
	 * @param aptitudes the aptitudes to set
	 */
	public void setAptitudes(Set<String> aptitudes) {
		this.aptitudes = aptitudes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Linkedin [id=" + id + ", id_demanda=" + id_demanda + ", experiencia=" + experiencia + ", educacion="
				+ educacion + ", aptitudes=" + aptitudes + "]";
	}
	
	
}

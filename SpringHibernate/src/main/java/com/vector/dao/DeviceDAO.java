package com.vector.dao;

import java.util.List;

import com.vector.model.WkstDevice;

public interface DeviceDAO {

	/**
	 * M�todo encargado de buscar dispositivo por Id.
	 * @param id Identificador del dispositivo
	 * @return Devuelve el dispositivo.
	 */
	public WkstDevice findById(long id);

	/**
	 * M�todo encargado de devolver todos los dispositivos
	 * @return
	 * Listado de todos los dispositivos.
	 */
	public List<WkstDevice> findAll();

	/**
	 * M�todo que aniade un nuevo dispositivo.
	 * @param device
	 * Dispositivo que se va a aniadir.
	 */
	public void addDevice(WkstDevice device);

	/**
	 * M�todo encargado de devolver todos los dispositivos filtrados por el nombre l�gico
	 * @param logicalName
	 * Nombre l�gico.
	 * @return Listdo de dispositivos
	 */
	public List<WkstDevice> findByLogicalName(String logicalName);

	/**
	 * M�todo encargado de borrar un dispositivo
	 * @param id Identidicador del dispositivo
	 */
	public void delete(long id);

}
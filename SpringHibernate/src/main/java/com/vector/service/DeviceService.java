package com.vector.service;

import java.util.List;

import com.vector.model.WkstDevice;

public interface DeviceService {

	public WkstDevice findById(long id);

	public List<WkstDevice> findAll();

	public void addDevice(WkstDevice device);

	public List<WkstDevice> findByLogicalName(String logicalName);

	public void delete(long id);
}
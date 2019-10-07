package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.login.Sucursal;

public interface SucursalDao {
	
	public void save(Sucursal s);

	public void delete(Sucursal s);

	public void update(Sucursal s);
	
	public Sucursal consult(Long id);
	
	public String byId(Long id);
	
    
	public List<Sucursal> findAll();
	
	

}

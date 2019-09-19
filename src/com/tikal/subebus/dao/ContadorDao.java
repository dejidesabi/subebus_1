package com.tikal.subebus.dao;

import com.tikal.subebus.modelo.entity.Contador;
import com.tikal.subebus.modelo.entity.Serial;

public interface ContadorDao {
	public void guardar(Contador c);
	public void crear();
	public Long getFolio();
	public void incrementa();
	
}

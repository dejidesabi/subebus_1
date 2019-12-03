package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.entity.Alerta;

public interface AlertaDAO {

	void add(Alerta a);
	
	List<Alerta> consultar();
	
	void delete(Alerta a);
	
	Alerta consultar(Long id);
}

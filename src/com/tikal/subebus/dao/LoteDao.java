package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.entity.Lote;

public interface LoteDao {

	public List<Lote> todos();
	
	public void guardar(Lote l);
	

	
	
}

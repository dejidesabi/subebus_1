package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;

public interface LoteDao {

	public List<Lote> todos();
	
	public void guardar(Lote l);
	
	public int pags();
	
	public List<Lote> findAllPage(int page);
	
	public Lote cargar(Long id);
	
	public void eliminar(Lote l);
}

package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.RutaBus;

public interface RutaBusDao {
	
	public List<RutaBus> todos();
	
	public void guardar(RutaBus r);

	public RutaBus cargar(Long idRutaBus);
	
	public RutaBus byChofer(String chofer);
	
	public List<RutaBus> byTipo(String tipo);
	
//	public int pags();
	
//	public List<Lote> findAllPage(int page);

}

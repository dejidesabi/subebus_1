package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.RutaBus;

public interface RutaBusDao {
	
	public List<RutaBus> todos();
	
	public void guardar(RutaBus r);
	
//	public int pags();
	
//	public List<Lote> findAllPage(int page);

}

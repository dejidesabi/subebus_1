package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.RutaMem;

public interface RutaMemDao {
	
	public List<RutaMem> todos();
	
	public void guardar(RutaMem rm);
	
	public int pags();
	
	public List<RutaMem> findAllPage(int page);


}

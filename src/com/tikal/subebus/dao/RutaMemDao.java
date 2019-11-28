package com.tikal.subebus.dao;

import java.util.Date;
import java.util.List;

import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.RutaMem;
import com.tikal.subebus.modelo.entity.Venta;

public interface RutaMemDao {
	
	public List<RutaMem> todos();
	
	public RutaMem cargar(Long idRutaMem);
	
	public void guardar(RutaMem rm);
	
	public int pags();
	
	public List<RutaMem> findAllPage(int page);
	
    public List<RutaMem> bySuc(Long idSucursal);
	
	public int pagsSuc(Long idSucursal);
	
	public List<RutaMem> bySucPage(int page,Long idSucursal);
	
	public List<RutaMem> periodoTodas(Date inicio, Date fin); 
	
	public List<RutaMem> periodoSuc(Date inicio, Date fin, Long idSucursal); 


}

package com.tikal.subebus.dao;

import java.util.Date;
import java.util.List;


import com.tikal.subebus.modelo.entity.Venta;

public interface VentaDao {
	
	public List<Venta> todas();
	
	public Venta cargar(Long idVenta);
	
	public void guardar(Venta v);
	
	public List<Venta> bySucursal(Long idSucursal);
	
	public List<Venta> byTipo(String tipo);
	
	public int pags();
	
	public List<Venta> findAllPage(int page);
	
	public Venta byMembresia(Long idMembresia);
	
	public List<Venta> periodoTodas(Date inicio, Date fin); 
	
	public List<Venta> periodoSuc(Date inicio, Date fin, Long idSucursal); 


}

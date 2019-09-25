package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.entity.Membresia;

public interface MembresiaDao {
	public void crear(Membresia m);
	
	
	public void actualizar(Membresia m);

	public Membresia consultar(Long id);
	
	public void eliminar(Membresia m);
	
	public List<Membresia> consultarTodos();
	
	public int pags();
	
	public List<Membresia> findAllPage(int page);
	
	public List<Membresia> byLote(Long idLote);
	public List<Membresia> byLoteA(Long idLote);
	public List<Membresia> byLoteI(Long idLote);
	
	public List<Membresia> byDT(String duracion, String tipo);

}

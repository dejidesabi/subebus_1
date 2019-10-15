package com.tikal.subebus.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.VentaDao;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.RutaBus;
import com.tikal.subebus.modelo.entity.Venta;

@Service("ventaDao")
public class VentaDaoImpl implements VentaDao{

	@Override
	public List<Venta> todas() {
		return ofy().load().type(Venta.class).list();
	}

	@Override
	public void guardar(Venta v) {
		ofy().save().entity(v).now();
		
	}

	@Override
	public List<Venta> bySucursal(String idSucursal) {
		 List<Venta> list=ofy().load().type(Venta.class).filter("idSucursal",idSucursal).list();
		 
		 return list;
	}

	@Override
	public List<Venta> byTipo(String tipo) {
		List<Venta> list=ofy().load().type(Venta.class).filter("tipo",tipo).list();
		 
		 return list;
	}

	@Override
	public int pags() {
		return ((ofy().load().type(Venta.class).count()-1)/15)+1;
	}

	@Override
	public List<Venta> findAllPage(int page) {
		return ofy().load().type(Venta.class).offset((page-1)*15).limit(10).list();
	}

}
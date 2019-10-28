package com.tikal.subebus.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.RutaMemDao;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.RutaMem;

@Service("rmDao")
public class RutaMemDaoImpl implements RutaMemDao {

	@Override
	public List<RutaMem> todos() {
		return ofy().load().type(RutaMem.class).list();
	}

	@Override
	public void guardar(RutaMem rm) {
		ofy().save().entity(rm).now();
		
	}

	@Override
	public int pags() {
		return ((ofy().load().type(RutaMem.class).count()-1)/15)+1;
	}

	@Override
	public List<RutaMem> findAllPage(int page) {
		return ofy().load().type(RutaMem.class).offset((page-1)*15).limit(10).list();
	}
	
	@Override
	public List<RutaMem> bySuc(Long idSucursal) {
		return ofy().load().type(RutaMem.class).filter("sucursal", idSucursal).list();
	}

	@Override
	public int pagsSuc(Long idSucursal) {
		return ((ofy().load().type(RutaMem.class).filter("sucursal", idSucursal).count()-1)/15)+1;
	}

	@Override
	public List<RutaMem> bySucPage(int page, Long idSucursal) {
		return ofy().load().type(RutaMem.class).filter("sucursal", idSucursal).offset((page-1)*15).limit(10).list();
	}

	@Override
	public RutaMem cargar(Long idRutaMem) {
		return ofy().load().type(RutaMem.class).id(idRutaMem).now();
		
	}


}

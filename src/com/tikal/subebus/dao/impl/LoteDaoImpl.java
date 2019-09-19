package com.tikal.subebus.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.LoteDao;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Service("loteDao")
public class LoteDaoImpl implements LoteDao{
	@Override
	public void guardar(Lote l) {
		ofy().save().entity(l).now();
	}

	@Override
	public List<Lote> todos() {
		return ofy().load().type(Lote.class).list();
	}
	
	@Override
	public int pags() {
		return ((ofy().load().type(Lote.class).count()-1)/15)+1;
	}

	@Override
	public List<Lote> findAllPage(int page) {
		return ofy().load().type(Lote.class).offset((page-1)*15).limit(10).list();
	}

}

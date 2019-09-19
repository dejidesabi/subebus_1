package com.tikal.subebus.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.LoteDao;
import com.tikal.subebus.modelo.entity.Lote;
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

}

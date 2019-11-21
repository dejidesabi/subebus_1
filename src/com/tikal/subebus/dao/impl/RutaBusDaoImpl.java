package com.tikal.subebus.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.RutaBusDao;
import com.tikal.subebus.modelo.entity.RutaBus;

@Service ("rbDao")
public class RutaBusDaoImpl implements RutaBusDao {

	@Override
	public List<RutaBus> todos() {
			return ofy().load().type(RutaBus.class).list();
		}

	@Override
	public void guardar(RutaBus r) {
		ofy().save().entity(r).now();
		
	}

	@Override
	public RutaBus byChofer(String chofer) {
		// TODO Auto-generated method stub
		return ofy().load().type(RutaBus.class).filter("chofer",chofer).list().get(0);
	}

	@Override
	public List<RutaBus> byTipo(String tipo) {
		// TODO Auto-generated method stub
		return ofy().load().type(RutaBus.class).filter("tipo",tipo).list();
	}

	@Override
	public RutaBus cargar(Long idRutaBus) {
		// TODO Auto-generated method stub
		return ofy().load().type(RutaBus.class).id(idRutaBus).now();
	}

}

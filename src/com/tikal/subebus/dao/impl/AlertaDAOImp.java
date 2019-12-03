package com.tikal.subebus.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.tikal.subebus.dao.AlertaDAO;
import com.tikal.subebus.modelo.entity.Alerta;


@Service("alertaDao")
public class AlertaDAOImp implements AlertaDAO{

	@Override
	public void add(Alerta a) {
		ofy().save().entity(a).now();
	}

	@Override
	public List<Alerta> consultar() {
		
		return ofy().load().type(Alerta.class).list();
	}

	@Override
	public void delete(Alerta a) {
		ofy().delete().entity(a).now();
	}

	@Override
	public Alerta consultar(Long id) {
		List<Alerta> lista=ofy().load().type(Alerta.class).filter("idProducto",id).list();
		if(lista.size()>0){
			return lista.get(0);
		}
		return null;
	}

}

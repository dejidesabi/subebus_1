package com.tikal.subebus.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.SerialDAO;
import com.tikal.subebus.modelo.entity.Serial;
import static com.googlecode.objectify.ObjectifyService.ofy;
@Service("serialdao")
public class SerialDAOImp implements SerialDAO{

	@Override
	public void guardar(Serial s) {
		s.setId(s.getRfc()+s.getSerie());
		ofy().save().entity(s).now();
	}

	@Override
	public Serial consultar(String rfc, String serie) {
		return ofy().load().type(Serial.class).id(rfc+serie).now();
	}

	@Override
	public List<Serial> consultar(String rfc) {
		return ofy().load().type(Serial.class).filter("rfc",rfc).list();
	}

	@Override
	public void eliminar(String id) {
		ofy().delete().type(Serial.class).id(id).now();
		
	}
	
}

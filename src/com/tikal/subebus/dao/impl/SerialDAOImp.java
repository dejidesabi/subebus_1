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
		//s.setId(s.);
		ofy().save().entity(s).now();
	}



	@Override
	public void eliminar(String id) {
		ofy().delete().type(Serial.class).id(id).now();
		
	}
	
}

package com.tikal.subebus.dao.impl;

import java.util.List;

import com.tikal.subebus.dao.ContadorDao;
import com.tikal.subebus.modelo.entity.Contador;
import static com.googlecode.objectify.ObjectifyService.ofy;


public class ContadorDaoImpl implements ContadorDao{
	@Override
	public void crear() {
		
		List<Contador> l=ofy().load().type(Contador.class).list();
		if(l.size()==0){
			Contador c = new Contador();
			c.setFolio(Long.parseLong("1000"));
			ofy().save().entity(c).now();
		}
	}

	@Override
	public Long getFolio() {
		List<Contador> l=ofy().load().type(Contador.class).list();
		if(l.size()<1){
			this.crear();
			return Long.parseLong("1000");
		}
		return l.get(0).getFolio();
	}

	@Override
	public void incrementa() {
		List<Contador> l=ofy().load().type(Contador.class).list();
		Contador c= l.get(0);
		c.setFolio(c.getFolio()+1);
		ofy().save().entity(c).now();
	}

}

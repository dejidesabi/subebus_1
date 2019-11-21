package com.tikal.subebus.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.FolioDao;
import com.tikal.subebus.modelo.entity.Folio;

@Service("folioDao")

public class FolioDaoImpl implements FolioDao{
	
	@Override
	public void incFolio() {
		List<Folio> list= ofy().load().type(Folio.class).list();
		if (list.size()==0 || list==null){
			this.crear();
		}else{
		
			Folio f=list.get(0);
			f.folio= f.folio+1;
			ofy().save().entity(f).now();
		}
	}



	@Override
	public void crear() {
		Folio f= new Folio();
		f.folio=1000;
		
		
		ofy().save().entity(f).now();
		System.out.println("inicia en :"+f.getFolio());
		
	}

	


	@Override
	public int getFolio() {
		//Folio f= ofy().load().type(Folio.class).filter("folioGabinete",folioGabinete).list().get(0);
		
		
		List<Folio> list= ofy().load().type(Folio.class).list();
		if (list.size()==0 || list==null){
			this.crear();
			Folio f= ofy().load().type(Folio.class).list().get(0);
			return f.folio ;
		}else{
		
			Folio f=list.get(0);
			return f.folio;
		}
		
		
		
	}
	
	
	
	



}

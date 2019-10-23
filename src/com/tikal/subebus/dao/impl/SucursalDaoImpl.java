package com.tikal.subebus.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.SucursalDao;
import com.tikal.subebus.modelo.login.Sucursal;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Service("sucursalDao")
public class SucursalDaoImpl implements SucursalDao {
	
	 public  void save(Sucursal s) {    	
	        ofy().save().entity(s).now();
	    }

	    
	    public void delete(Sucursal s) {
	    	
	        ofy().delete().entity(s).now();
	       
	    }

	
		public void update(Sucursal s) {
		
		   Sucursal old = this.consult(s.getId());
	
			if (old != null) {
				old.setNombre(s.getNombre());
				//old.setCurp(s.getCurp());
				old.setDomicilio(s.getDomicilio());
				old.setRfc(s.getRfc());
				old.setTelefono(s.getTelefono());
				old.setTitular(s.getTitular());
				old.setUbicacion(s.getUbicacion());
				
				
			}

				ofy().save().entity(old);
	   }

	    
	
		public Sucursal consult(Long id) {
		 
	      return ofy().load().type(Sucursal.class).id(id).now();
			
		}


	   
		public List<Sucursal> findAll() {
			List<Sucursal> lista= ofy().load().type(Sucursal.class).list();
			for (Sucursal s:lista){
				if (s.getId()==9999){
					lista.remove(s);
					break;
				}
			}
			return lista;
			
		}


		@Override
		public String byId(Long id) {
			 		Sucursal s=  ofy().load().type(Sucursal.class).id(id).now();
			  return s.getNombre();
		}


		



}
